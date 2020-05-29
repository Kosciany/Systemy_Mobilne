import numpy as np
import cv2

HEIGHT = 600
WIDTH = 800
RGB_SCALE = 3

ROWS = 3
COLS = 4

blank_image = np.zeros((HEIGHT,WIDTH,RGB_SCALE), np.uint8)
blank_image[blank_image == 0 ] = 255

for i in range(1, HEIGHT):
    for j in range(1, WIDTH): 
        for k in range(0, RGB_SCALE):
            if not((i % (HEIGHT/ROWS)) and int((j % (WIDTH/COLS)))):
                blank_image[i][j][k] = 0

cv2.imwrite("puzzletemplate.jpg",blank_image)