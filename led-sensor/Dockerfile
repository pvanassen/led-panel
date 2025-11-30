FROM debian:bookworm-slim

RUN apt-get update && apt-get install -y --no-install-recommends python3 python3-dev python3-pip
RUN useradd -u 1000 -ms /bin/false app

WORKDIR /app

USER app

COPY *.py /app/
COPY requirements.txt /app/

WORKDIR /app

RUN pip3 install --prefer-binary --break-system-packages -r requirements.txt

ENTRYPOINT ["python3", "main.py"]
