from datetime import datetime, timedelta
import random


class Timer:
    def __init__(self, start_time, end_time):
        self.start_time = start_time
        self.end_time = end_time
        self.current_time = start_time

    def get_random_seconds(self):
        random_seconds = random.randint(0, 59)
        return self.current_time.replace(second=random_seconds).time()

    def next_minute(self):
        self.current_time += timedelta(minutes=1)
        if (self.current_time >= self.end_time):
            return None
        return self.current_time

    def __str__(self):
        return self.current_time.time() 


