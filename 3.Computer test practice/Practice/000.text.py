import sys


def solve():
    line1 = sys.stdin.readline().strip()
    if not line1:
        return []
    cores = list(map(int, line1.split()))

    cores.sort()

    line2 = sys.stdin.readline().strip()
    num = int(line2)

    link1 = []
    link2 = []

    for core in cores:
        if core < 4:
            link1.append(core)
        else:
            link2.append(core)
    if num == 1:
        if len(link1) == 1:
            return link1
        elif len(link2) == 1:
            return link2
        elif len(link1) == 3:
            return link1
        elif len(link2) == 3:
            return link2
        elif len(link1) == 2:
            return link1
        elif len(link2) == 2:
            return link2
        elif len(link1) == 4:
            return link1
        elif len(link2) == 4:
            return link2
        else:
            return []
    if num == 2:
        if len(link1) == 2:
            return link1
        elif len(link2) == 2:
            return link2
        elif len(link1) == 4:
            return link1
        elif len(link2) == 4:
            return link2
        elif len(link1) == 3:
            return link1
        elif len(link2) == 3:
            return link2
        else:
            return []
    if num == 4:
        if len(link1) == 4:
            return link1
        elif len(link2) == 4:
            return link2
        else:
            return []
    if num == 8:
        if len(cores) == 0:
            return cores


if __name__ == "__main__":
    print(solve())