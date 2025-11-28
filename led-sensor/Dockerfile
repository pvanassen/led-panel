FROM debian:bookworm-slim@sha256:c0e01efb9abdf122a7a07b891fafeab41b8981edc8ce9318422b1ff87220333c

RUN apt-get update && apt-get install -y --no-install-recommends python3 python3-dev python3-pip
RUN useradd -u 1000 -ms /bin/false app

WORKDIR /app

USER app

COPY *.py /app/
COPY requirements.txt /app/

WORKDIR /app

RUN pip3 install --prefer-binary --break-system-packages -r requirements.txt

ENTRYPOINT ["python3", "main.py"]