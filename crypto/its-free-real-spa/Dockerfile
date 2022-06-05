FROM ruby:3.1.2-bullseye
EXPOSE 9999
ENV RACK_ENV=production
RUN bundle config set --global frozen 1 && \
    bundle config set --global without 'development'

WORKDIR /app
COPY Gemfile Gemfile.lock ./
RUN bundle install

COPY flag.txt main.rb key-storage.rb ./

ENTRYPOINT ["bundle", "exec", "ruby"]
CMD ["main.rb"]
