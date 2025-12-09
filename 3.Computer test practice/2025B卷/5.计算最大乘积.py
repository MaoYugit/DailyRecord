import pandas as pd
from datetime import datetime, timedelta

# 创建完整数据
data = [
    ("樊樊", 36942), ("小七", 35751), ("小钰", 27217), ("君君", 29581), ("小冉", 23260),
    ("小乖", 19942), ("周一", 19129), ("小柔", 17771), ("南希", 20034), ("小雨", 11517),
    ("小瑶", 5849), ("周周", 11428), ("香香", 2843), ("小肖", 17141), ("松松", 17331),
    ("秋子", 16939), ("淼淼", 11134), ("杏紫", 15716), ("琪琪", 14074), ("雨柔", 16146),
    ("贝贝", 9847), ("豆豆", 18023), ("小诺", 8776), ("小雪", 11990), ("桃桃", 15087),
    ("玫瑰", 13362), ("困困", 8391), ("颜婉", 1512), ("小蛇", 6529), ("汁汁", 13728),
    ("筱筱", 14441), ("美美", 13194), ("冰冰", 8682), ("小梦", 11082), ("点点", 8631),
    ("呆呆", 9010), ("小熊", 1809), ("白小白", 16211), ("小依", 922), ("肉肉", 4120),
    ("小言", 488), ("鹿鹿", 7683), ("佳怡", 5297), ("小悦", 7095), ("柚柚", 1845), ("时韵", 2573)
]

# 标黄供应商名单
yellow_list = ["小瑶", "香香", "小熊", "小依", "肉肉", "小言"]

# 分组
yellow_suppliers = []
normal_suppliers = []

for name, amount in data:
    if name in yellow_list:
        yellow_suppliers.append((name, amount))
    else:
        normal_suppliers.append((name, amount))

print(f"标黄供应商（从20号开始，共{len(yellow_suppliers)}人）:")
for name, amount in yellow_suppliers:
    print(f"  {name}: {amount}元")

print(f"\n未标黄供应商（从15号开始，共{len(normal_suppliers)}人）:")
for name, amount in normal_suppliers:
    print(f"  {name}: {amount}元")


# 生成打款计划
def generate_payment_plan(suppliers, start_date_str, start_day):
    """生成打款计划"""
    plan = []
    start_date = datetime(2023, 10, start_day)  # 假设是2023年10月

    for name, total_amount in suppliers:
        remaining = total_amount
        current_date = start_date

        while remaining > 0:
            daily_amount = min(remaining, 5000)
            plan.append({
                "日期": current_date.strftime("%Y-%m-%d"),
                "姓名": name,
                "打款金额": daily_amount,
                "累计已付": total_amount - remaining + daily_amount,
                "剩余待付": remaining - daily_amount
            })

            remaining -= daily_amount
            current_date += timedelta(days=1)

    return pd.DataFrame(plan)


# 生成未标黄供应商的计划（从15号开始）
normal_plan = generate_payment_plan(normal_suppliers, "2023-10-15", 15)

# 生成标黄供应商的计划（从20号开始）
yellow_plan = generate_payment_plan(yellow_suppliers, "2023-10-20", 20)

# 合并计划
full_plan = pd.concat([normal_plan, yellow_plan], ignore_index=True)

# 按日期排序
full_plan = full_plan.sort_values("日期").reset_index(drop=True)

# 计算每日打款总额
daily_summary = full_plan.groupby("日期")["打款金额"].sum().reset_index()
daily_summary.columns = ["日期", "当日打款总额"]

print(f"\n总打款天数: {full_plan['日期'].nunique()}天")
print(f"总打款记录数: {len(full_plan)}条")
print(f"总打款金额: {full_plan['打款金额'].sum():,}元")

# 保存到Excel
with pd.ExcelWriter("每日打款计划.xlsx") as writer:
    # 完整打款计划
    full_plan.to_excel(writer, sheet_name="完整打款计划", index=False)

    # 每日汇总
    daily_summary.to_excel(writer, sheet_name="每日汇总", index=False)

    # 按供应商汇总
    supplier_summary = pd.DataFrame([
        {"姓名": name, "总金额": amount, "开始日期": "10月20日" if name in yellow_list else "10月15日"}
        for name, amount in data
    ])
    supplier_summary.to_excel(writer, sheet_name="供应商汇总", index=False)

    # 每日详细名单
    for date in sorted(full_plan["日期"].unique()):
        day_plan = full_plan[full_plan["日期"] == date]
        # 简化工作表名称（去掉横线）
        sheet_name = date.replace("-", "")
        # Excel工作表名称最多31个字符
        day_plan.to_excel(writer, sheet_name=f"{sheet_name}", index=False)

print("\n打款计划已生成并保存到: 每日打款计划.xlsx")
print("文件中包含以下工作表:")
print("1. 完整打款计划 - 所有打款记录的详细列表")
print("2. 每日汇总 - 每天打款总额")
print("3. 供应商汇总 - 每个供应商的总金额和开始日期")
print("4. 每日详细名单 - 按日期分开的每日打款名单")