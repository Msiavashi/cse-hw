from Timer import Timer
from datetime import datetime
from DataGenerator import DataGenerator
timer = Timer(datetime(2021, 1, 2, 8, 29, 0), datetime(2021, 1, 2, 12, 30, 0))
dataGenerator = DataGenerator(40, 10000, 100, 100, 1, timer)
dataGenerator.start()