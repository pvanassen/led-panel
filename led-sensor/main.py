from flask import Flask, Response, request
import pigpio
import DHT
import bh1750
from time import sleep

app = Flask(__name__)

DHT11_pin = 7
pi = pigpio.pi()
dht = DHT.sensor(pi, DHT11_pin, 1)
sensor = bh1750.BH1750(pi)

strips = [16, 20, 21, 26, 19, 13, 6, 5]

@app.route("/")
def main():
    lux = sensor.lux
    dht_data = dht.read()
    humi = '{0:0.1f}'.format(dht_data[3])
    temp = '{0:0.1f}'.format(dht_data[4])
    lux = "{0:0.1f}".format(lux)
    return Response('{"lux":' + lux + ',"hum":' + humi + ',"temp":' + temp + '}', mimetype='application/json')

@app.route("/flash")
def flash():
    for gpio in strips:
        pi.set_PWM_dutycycle(gpio, 255)
    sleep(0.05)
    for gpio in strips:
        pi.set_PWM_dutycycle(gpio, 0)
    return Response('{"result":true}', mimetype='application/json')

@app.route("/on", defaults={'strip': None})
@app.route("/on/<strip>")
def on(strip):
    if strip:
        pi.set_PWM_dutycycle(strips[int(strip)], 255)
    else:
        for gpio in strips:
            pi.set_PWM_dutycycle(gpio, 255)
    return Response('{"result":true}', mimetype='application/json')

@app.route("/off", defaults={'strip': None})
@app.route("/off/<strip>")
def off(strip):
    if strip:
        pi.set_PWM_dutycycle(strips[int(strip)], 0)
    else:
        for gpio in strips:
            pi.set_PWM_dutycycle(gpio, 0)
    return Response('{"result":true}', mimetype='application/json')

@app.route("/state", methods=['POST'])
def state():
    state_data = request.form["b"].encode()
    for strip in range(len(strips)):
        pi.set_PWM_dutycycle(int(strips[strip]), state_data[strip])
    return Response('{"result":true}', mimetype='application/json')

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=8090, debug=True)