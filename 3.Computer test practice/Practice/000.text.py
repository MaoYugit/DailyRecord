import sys

def solve():
    try:
        line = sys.stdin.readline().strip()
        if not line:
            return
        array = list(map(int, line.split()))
    except ValueError:
        print([])
        return

    n = len(array)
    for i in range(0, n-1):
        if i % 2 == 0:
            if array[i] < array[i+1]:
                array[i], array[i+1] = array[i+1], array[i]
        else:
            if array[i] > array[i+1]:
                array[i], array[i+1] = array[i+1], array[i]
    print(" ".join(str(num) for num in array))

if __name__ == '__main__':
    solve()