# "It's free real SPA"

_Author: Lena64_t_  
_Tags: Crypto_

## Description

> You're a slavic warrior in a slavic kingdom acquired and defended by ancestors of yours for years. Your tired body needs refreshment - preferably sauna. You're not gonna pay those pigs for such fundamental service, right?! (Please say, You won't!)  
>
> Their Ultra-Advanced-Corporateresourceplaningsystem (UAC) is FOSS (sounds German, I know). Grab sauce [here]('#link to the code as a static asset').

## How to run

In both cases Puma (Ruby HTTP server) starts listening on all IPs(`0.0.0.0`) and port `9999`

### Docker

```sh
docker build --network host -t lena64_t/its-free-real-spa .
docker container run -d --rm --name -p 9999:9999 ifrs lena64_t/its-free-real-spa
```

### Standalone

```sh
bundle install --without=development
bundle exec ruby main.rb
```

## Solution

Generally source code is documented and shows step-by-step what is done.

### Familiarise yourself with the page  

1. There is `/sauna/` page with access denied
2. There is `/pool/` page with access denied
3. There is `/promo/` page with link to the pool
4. Link links to the `/pool/<valid token>` page
5. If You took a peek on source code you may check `/debug/test/<token from d:>` it's for your convinience, but is not required to solve this challenge

### Familiarise yourself with the source code

1. Token is created by `sign`ing string in format `@name|@asses_joined_by_coma`
2. Signing is made by prefixing message with digest of `@key + msg`. That construction is vulnerable for [length-extension attack](https://en.wikipedia.org/wiki/Length_extension_attack)
3. Digest is SHA-0

### Writing exploit

> This exploit uses `cifroteko` library for ruby which simplifies the attack.  
> You can write this attack manualy ex. in Python 
> but then you need to pad messages manualy as described in specification of SHA-0
> (or generally in [Merkle–Damgård construction](https://en.wikipedia.org/wiki/Merkle%E2%80%93Damg%C3%A5rd_construction)).

1. Get valid token for the pool
2. Split to get message and hash
```rb
hash = raw_token[...20]
msg  = raw_token[20...]
```
3. Hash is valid for message in format
```
|<---------------------- our hash value includes ---------------------------->|
| key of some len |  msg(many blocks)   |  padding valid for len of key+msg   |
|^we dont know len|  ^ known            | we can calculate if we guess keylen |
```
4. We want to add something at the end, but we must include old padding which is already 'baked into' digest - sadly we have to try every possible value for key length
```rb
# @digest is set to Cifroteko::Digest::Sha0 in exploit-class constructor

offsets_to_check.each do |ofs| # For each offset in range
  msg_pad = @digest.pad(msg, offset: ofs).as_str # Pad assuming keylen of `ofs` (and converting bytes to string)
  ...
end
```
5. Then we can append our suffix ex. `,sauna,` to the token
```
|<---------------------- our hash value includes ---------------------------->|<--- our hash does not include
| key of some len |  msg(many blocks)   |  padding valid for len of key+msg   | ,sauna,
|^we dont know len|  ^ known            | we can calculate if we guess keylen | ^suffix
```
In code:
```rb
msg_pad_suffix = msg_pad + suffix
```
6. Then we need to properly pad the thing, to make it 'digestible'. We want:
```
|<---------------------- our hash value includes ---------------------------->|<-------------- our hash does not include ---------------->|
| key of some len |  msg(many blocks)   |  padding valid for len of key+msg   | ,sauna, | padding valid for len of key+msg+oldpad+suffix  |
|^we dont know len|  ^ known            | we can calculate if we guess keylen | ^suffix | ^we can calculate that if we know keylen, still |
```
Corresponding ruby:
```rb
msg_pad_suffix_pad = @digest.pad(msg_pad_suffix, offset: ofs ).as_str # Add padding valid for message of length ofs+msg+oldpad+suffix
```

7. Now we have properly padded extended message that should be valid input for digest (assuming `keylen=ofs`). Now we have to:  
7.1. Rip apart the hash into registers that MD works on  
7.2. Feed added blocks to a compression function of the digest(SHA0)  
7.3. Feed them with old hash value(registers) as an IV  
7.4. Merge them back into the hash  
```rb
# Copied from cifroteko's Merkle–Damgård construction implemented in `lib/cifroteko/scheme/md_construct.rb`

in_state = @digest.divide(hash) # 7.1 Ripping hash apart
out_state = []

#| Process everyting we added      |asbytes| in slices of 512bits (SHA0's internal block size)
msg_pad_suffix_pad[msg_pad.length...].bytes.each_slice(@digest::InternalBlockSize) do |chunk| # 7.2 + 7.3
    out_state = @digest.process_chunk(chunk, in_state) # Run compression function on `chunk` with IV: in_state (splitted hash)
    in_state = in_state.zip(out_state).map { |i,o| (i+o) & 0xffffffff } # Merge registers between round like normal SHA would
end

# Final token formatting
#
out_hash = @digest.merge(in_state) # Merge state into digest (7.4)
# Our final message is |msg|oldpad|suffix| with hash of |key|msg|oldpad|suffix| (if we guessed offset correctly)
out_msg = msg_pad_suffix 

# Merge into format required by application
out_token = "#{out_hash}#{out_msg}"
```

8. Check if token(its assumed length) is valid using `test_token_pool(out_token)`

9. That should be repeated for each of possible offsets as written in (4)
