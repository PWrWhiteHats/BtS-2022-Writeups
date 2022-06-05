FROM python:3.10-slim

WORKDIR /app

COPY ./ /app

RUN pip install -r requirements.txt

EXPOSE 12345 12345

# SET flag
# Flag should be placed in flag.txt file in root challange folder
# So it could be easily changed by administrators if needed
# Only first line of flag.txt file would be read
# Then if your setup require replacing flag in file at container startup
# (e.g. nginx.conf cannot read enviromental variables by default)
# you could use setup below. Otherwise your app could read flag.txt file directly
#ENV replaceFlagFile="/app/app.py"
#COPY flag.txt /flag.txt
#RUN flag=$(head -n 1 /flag.txt) && sed "s/--FLAG--/$flag/" $replaceFlagFile | sponge $replaceFlagFile

ENTRYPOINT ["python"]
CMD ["server.py"]