import sys

def solve():
    input_data = sys.stdin.read().split()

    if not input_data:
        return

    n = int(input_data[0])
    m = int(input_data[1])
    cards_num = list(map(int, input_data[2:]))

    if n > m:
        print(1)
        return

    yu_set = set()
    yu_set.add(0)
    cur_sum = 0

    for i in range(n):
        cur_sum += cards_num[i]
        yu = cur_sum % m
        if yu in yu_set:
            print(1)
            return
        yu_set.add(yu)
    print(0)

if __name__ == '__main__':
    solve()