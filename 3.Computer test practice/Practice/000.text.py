import sys


def solve():
    try:
        line1 = sys.stdin.readline().strip()
        if not line1:
            return
        n = int(line1)
        target_document = sys.stdin.readline().strip()
    except ValueError:
        return

    file_list = []
    for i in range(n):
        count = 0
        line = sys.stdin.readline().strip()
        for char in line:
            if char == "-":
                count += 1
                continue
            else:
                break
        folder = line[count:]
        if folder == target_document+ " -1":
            for j in range(i+1, n):
                file_line = sys.stdin.readline().strip()
                s_s = "-" * (count + 4)
                if not file_line.startswith(s_s):
                    break
                cur_list = file_line.split()
                if cur_list[1] == "-1":
                    continue
                else:
                    cur_file_name = ""
                    for char in cur_list[0]:
                        if char == "-":
                            continue
                        else:
                            cur_file_name += char
                    file_list.append((cur_file_name, cur_list[1]))
                break

    if len(file_list) == 0:
        print("No file")
    file_list.sort(key=lambda x:x[1])

    for file_name, create_time in file_list:
        print(file_name, create_time)

if __name__ == '__main__':
    solve()