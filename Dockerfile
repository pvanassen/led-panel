FROM balenalib/raspberrypi3-python:3.11-latest-build

RUN useradd -u 1000 -ms /bin/false app

WORKDIR /app

USER app

COPY main.py /app/
COPY requirements.txt /app/

WORKDIR /app

RUN pip3 install --prefer-binary -r requirements.txt

ENTRYPOINT ["python3", "main.py"]