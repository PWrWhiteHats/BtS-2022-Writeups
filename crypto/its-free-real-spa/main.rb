require 'cifroteko'
require 'sinatra'
require './key-storage.rb'

set :bind, '0.0.0.0'
set :port, 9999

class Hmac_Sha
  def initialize(key)
    @key = key
  end

  def sign(msg)
    Cifroteko::Digest::Sha0.digest( @key + msg ) + msg
  end

  def valid?(msg_with_hash)
    return false if msg_with_hash.length < 21

    msg = msg_with_hash[20...]

    sign(msg) == msg_with_hash
  end
end

class SubscriptionToken
  attr_reader :name, :asses

  Hmacker = Hmac_Sha.new($SIGNING_KEY)

  def initialize(name, allowed_services)
    @name = name
    @asses = allowed_services
  end

  def to_s
    Hmacker.sign("#{@name}|#{@asses.join(',')}").to_hex
  end

  def has_access?(place)
    @asses.include?(place)
  end

  def self.from_s(hex_string)
    string = hex_string.from_hex
    return nil unless Hmacker.valid?(string)
    token = string[20...]

    splitted = token.split('|')
    name = splitted[0]
    asses = splitted[1].split(',')
    SubscriptionToken.new(name, asses)
  end
end

get '/' do
  <<EOP
  <body bgcolor="rainbowdash">
    <center>
      <marquee><blink>
        <h1>
          Welcome to the <b>L3333TEST</b> SPA in <u>the world</u>
        </h1>
      </blink></marquee>
      <h2> Go to the <a href="/sauna/"> sauna </a> </h2>
      <h2> Go to the <a href="/pool/">  pool  </a> </h2>
      <h2> Check out our <a href="/promo/">promotions</a> </h2>
    </center>
  </body>
EOP
end

get '/sauna/?' do
  <<EOP
    <center>
      You need a subscription card to enter the sauna <br />
      <a href="/"> Go back </a> <br />
      <br />
    </center>
EOP
end

get '/sauna/:token' do |token|
  return 'You must have a valid subscription to enter the sauna' unless SubscriptionToken.from_s(token)&.has_access?('sauna')

  <<EOP
<center>
  Congrats! Welcome to the sauna queen(or king). <br />
  <iframe src="https://giphy.com/embed/l2JefsktJIlbcyA3C" width="480" height="270" frameBorder="0"></iframe>
  <hr />
  Here is the flag: <br />
  >>>>>> #{$CTF_FLAG} <<<<<<
<hr />
</center>
EOP
end

get '/pool/?' do
<<EOP
    <center>
      You need a subscription card to enter the pool <br />
      <a href="/"> Go back </a> <br />
      <br />
    </center>
EOP
end

get '/pool/:token' do |token|
  subs_token = SubscriptionToken.from_s(token)
  return 'You must have a valid subscription to enter the pool' unless subs_token&.has_access?('pool')

  <<EOP
    <center>
      Welcome to the pool #{subs_token.name} <br />
      <iframe src="https://giphy.com/embed/A6aHBCFqlE0Rq" width="480" height="360" frameBorder="0"></iframe>
    </center>
EOP
end

get '/promo/' do
  free_token_name = 'Anon_' + Random.urandom(4).to_hex
  free_pool_token = SubscriptionToken.new(free_token_name, ['pool']).to_s
  <<EOP
    <center>
      Today is your lucky day, we have free pool tickets on #{Time.now.strftime('%A')}s <br />
      <a href="/pool/#{free_pool_token}"> Go directly to the pool </a> 
    </center>
EOP
end

if settings.development?
  get '/debug/gen/:service' do |service|
    dbg_name = 'Admin_' + Random.urandom(4).to_hex
    dbg_token = SubscriptionToken.new(dbg_name, ['pool','sauna',service]).to_s
    "Here is your token master: #{dbg_token} <br /> Test link: <a href='/debug/test/#{dbg_token}'>link</a>"
  end
end

get '/debug/test/:token' do |token|
  st = SubscriptionToken.from_s(token)
  Rack::Utils.escape_html(st.inspect)
end