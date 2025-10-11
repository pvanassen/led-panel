from board import I2C
from flask import Flask, Response
import board
import adafruit_dht as DHT
import adafruit_bh1750 as BH1750

app = Flask(__name__)

# DHT11_pin = 23
#dht_device = DHT.DHT11(board.D4)
i2c = I2C()
sensor = BH1750.BH1750(i2c)


@app.route("/")
def main():
    lux = sensor.lux
#    humi = '{0:0.1f}'.format(dht_device.humidity)
#    temp = '{0:0.1f}'.format(dht_device.temperature)
    lux = "{0:0.1f}".format(lux)
    return Response('{"lux":' + lux + '}', mimetype='application/json')


if __name__ == "__main__":
    app.run(host='0.0.0.0', port=8090)