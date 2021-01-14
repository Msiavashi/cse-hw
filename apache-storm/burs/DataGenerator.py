import csv
import random


class DataGenerator:
    def __init__(self, num_symbols, num_exchange_codes, max_stock, price_coefficient, fluctuation_interval, timer):
        self.num_symbols = num_symbols
        self.num_exchange_codes = num_exchange_codes
        self.fluctuation_interval = fluctuation_interval
        self.price_coefficient = price_coefficient
        self.timer = timer
        self.max_stock = max_stock

    def get_trade_price(self, symbol):
        price = symbol * self.price_coefficient
        min_price = price - price * 0.1
        max_price = price + price * 0.1
        return random.randrange(min_price, max_price)

    def start(self):
        records = []
        records.append("random_time, exchange_code, trade_method, random_symbol, trade_price, random_stocks")
        while self.timer.next_minute():
            for exchange_code in range(self.num_exchange_codes):
                random_symbol = random.randint(1, self.num_symbols)
                trade_price = self.get_trade_price(random_symbol)
                random_time = self.timer.get_random_seconds()
                trade_method = random.randint(0, 1)    # 0: sell, 1: buy
                random_stocks = random.randint(1, self.max_stock)
                records.append("{0}, {1}, {2}, {3}, {4}, {5}".format(
                    random_time, exchange_code, trade_method, random_symbol, trade_price, random_stocks))
        self.write_csv(records)

    def write_csv(self, records):
        with open('out.csv', 'w', newline='') as csv_file:
            csv_writer = csv.writer(csv_file, delimiter=',')
            for line in records:
                csv_writer.writerow(line.split(','))
