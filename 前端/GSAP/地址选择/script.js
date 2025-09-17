const addressData = {
  浙江省: {
    杭州市: {
      西湖区: ["灵隐街道", "西溪街道"],
      滨江区: ["西兴街道", "长河街道"],
    },
    宁波市: {
      海曙区: ["鼓楼街道", "南门街道"],
      江北区: ["中马街道", "白沙街道"],
    },
  },
  广东省: {
    广州市: {
      天河区: ["天河南街道", "石牌街道"],
      越秀区: ["北京街道", "洪桥街道"],
    },
    深圳市: {
      福田区: ["园岭街道", "南园街道"],
      南山区: ["南头街道", "蛇口街道"],
    },
  },
};

document.addEventListener("DOMContentLoaded", () => {
  const provinceSelect = document.getElementById("province");

  // 初始化省份
  for (const province in addressData) {
    const option = new Option(province, province);
    provinceSelect.add(option);
  }
});

function updateCities() {
  const province = document.getElementById("province").value;
  const citySelect = document.getElementById("city");
  const cityContainer = citySelect.parentElement;

  // 清空并重置后续下拉框
  resetSelect(citySelect, "请选择市");
  resetSelect(document.getElementById("district"), "请选择区/县");
  resetSelect(document.getElementById("town"), "请选择乡镇");

  if (province && addressData[province]) {
    for (const city in addressData[province]) {
      citySelect.add(new Option(city, city));
    }
    // 显示并播放动画
    animateIn(cityContainer);
  } else {
    // 隐藏后续下拉框
    animateOut(cityContainer);
    animateOut(document.getElementById("district").parentElement);
    animateOut(document.getElementById("town").parentElement);
  }
}

function updateDistricts() {
  const province = document.getElementById("province").value;
  const city = document.getElementById("city").value;
  const districtSelect = document.getElementById("district");
  const districtContainer = districtSelect.parentElement;

  resetSelect(districtSelect, "请选择区/县");
  resetSelect(document.getElementById("town"), "请选择乡镇");

  if (city && addressData[province][city]) {
    for (const district in addressData[province][city]) {
      districtSelect.add(new Option(district, district));
    }
    animateIn(districtContainer);
  } else {
    animateOut(districtContainer);
    animateOut(document.getElementById("town").parentElement);
  }
}

function updateTowns() {
  const province = document.getElementById("province").value;
  const city = document.getElementById("city").value;
  const district = document.getElementById("district").value;
  const townSelect = document.getElementById("town");
  const townContainer = townSelect.parentElement;

  resetSelect(townSelect, "请选择乡镇");

  if (district && addressData[province][city][district]) {
    for (const town of addressData[province][city][district]) {
      townSelect.add(new Option(town, town));
    }
    animateIn(townContainer);
  } else {
    animateOut(townContainer);
  }
}

function resetSelect(selectElement, defaultOptionText) {
  selectElement.innerHTML = "";
  selectElement.add(new Option(defaultOptionText, ""));
  animateOut(selectElement.parentElement);
}

function animateIn(element) {
  if (element.style.display === "none") {
    element.style.display = "block";
    gsap.from(element, {
      duration: 0.5,
      y: -20,
      opacity: 0,
      ease: "power2.out",
    });
  }
}

function animateOut(element) {
  if (element.style.display === "block") {
    gsap.to(element, {
      duration: 0.3,
      y: -10,
      opacity: 0,
      ease: "power1.in",
      onComplete: () => {
        element.style.display = "none";
        // 重置 GSAP 应用的变换
        gsap.set(element, { clearProps: "all" });
      },
    });
  }
}
