class Solution:
    def dayOfYear(self, date: str) -> int:
        month_day = {
            1: 31,
            3: 31,
            5: 31,
            7: 31,
            8: 31,
            10: 31,
            12: 31,
            4: 30,
            6: 30,
            9: 30,
            11: 30
        }
        days = 0
        [year, month, day] = date.split('-')
        if (int(month) == 1){
        return int(day)
        } elif (int(month) == 2){
        return 31 + int(day)

        } else {
        for i in range(1, int(month)):
        if (i != 2){
        days += month_day[i]
        } else {
        if (int(year) % 4 == 0 and int(year) % 100 != 0){
        days += 28
        } else {
        days += 29
        }
        }
    }
    return days + int(day)

}
