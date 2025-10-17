const data = [
  {
    place: "Switzerland Alps",
    title: "SAINT",
    title2: "ANTONIEN",
    description:
      "Tucked away in the Switzerland Alps, Saint Antönien offers an idyllic retreat for those seeking tranquility and adventure alike. It's a hidden gem for backcountry skiing in winter and boasts lush trails for hiking and mountain biking during the warmer months.",
    image: "https://assets.codepen.io/3685267/timed-cards-1.jpg",
  },
  {
    place: "Japan Alps",
    title: "NANGANO",
    title2: "PREFECTURE",
    description:
      "Nagano Prefecture, set within the majestic Japan Alps, is a cultural treasure trove with its historic shrines and temples, particularly the famous Zenkō-ji. The region is also a hotspot for skiing and snowboarding, offering some of the country's best powder.",
    image: "https://assets.codepen.io/3685267/timed-cards-2.jpg",
  },
  {
    place: "Sahara Desert - Morocco",
    title: "MARRAKECH",
    title2: "MEROUGA",
    description:
      "The journey from the vibrant souks and palaces of Marrakech to the tranquil, starlit sands of Merzouga showcases the diverse splendor of Morocco. Camel treks and desert camps offer an unforgettable immersion into the nomadic way of life.",
    image: "https://assets.codepen.io/3685267/timed-cards-3.jpg",
  },
  {
    place: "Sierra Nevada - USA",
    title: "YOSEMITE",
    title2: "NATIONAL PARAK",
    description:
      "Yosemite National Park is a showcase of the American wilderness, revered for its towering granite monoliths, ancient giant sequoias, and thundering waterfalls. The park offers year-round recreational activities, from rock climbing to serene valley walks.",
    image: "https://assets.codepen.io/3685267/timed-cards-4.jpg",
  },
  {
    place: "Tarifa - Spain",
    title: "LOS LANCES",
    title2: "BEACH",
    description:
      "Los Lances Beach in Tarifa is a coastal paradise known for its consistent winds, making it a world-renowned spot for kitesurfing and windsurfing. The beach's long, sandy shores provide ample space for relaxation and sunbathing, with a vibrant atmosphere of beach bars and cafes.",
    image: "https://assets.codepen.io/3685267/timed-cards-5.jpg",
  },
  {
    place: "Cappadocia - Turkey",
    title: "Göreme",
    title2: "Valley",
    description:
      "Göreme Valley in Cappadocia is a historical marvel set against a unique geological backdrop, where centuries of wind and water have sculpted the landscape into whimsical formations. The valley is also famous for its open-air museums, underground cities, and the enchanting experience of hot air ballooning.",
    image: "https://assets.codepen.io/3685267/timed-cards-6.jpg",
  },
];

// =======  工具函数   ==========
const _ = (id) => document.getElementById(id);

// =======  HTML 生成   ==========
const cards = data
  .map(
    (i, index) =>
      `<div class="card" id="card${index}" style="background-image:url(${i.image})"></div>`
  )
  .join("");

const cardContents = data
  .map(
    (i, index) => `<div class="card-content" id="card-content-${index}">
  <div class="content-start"></div>
  <div class="content-place">${i.place}</div>
  <div class="content-title-1">${i.title}</div>
  <div class="content-title-2">${i.title2}</div>
  </div>`
  )
  .join("");

const sildeNumbers = data
  .map(
    (_, index) =>
      `<div class="item" id="slide-item-${index}" >${index + 1}</div>`
  )
  .join("");

// =======  HTML 注入   ==========
_("demo").innerHTML = cards + cardContents;
_("slide-numbers").innerHTML = sildeNumbers;

// ========= 简化选择器的工具函数 ==========
function getCard(index) {
  return `#card${index}`;
}

function getCardContent(index) {
  return `#card-content-${index}`;
}

function getSliderItem(index) {
  return `#slide-item-${index}`;
}

// ========= 增强GSAP的工具函数 ==========
function animate(target, duration, properties) {
  return new Promise((resolve) => {
    gsap.to(target, {
      ...properties,
      duration: duration,
      onComplete: resolve,
    });
  });
}

// ========= 全局状态变量 ==========
let order = [0, 1, 2, 3, 4, 5]; // 卡片顺序
let detailsEven = true; // 详情内容的动画状态

// ========= 布局与样式变量 ==========
let offsetTop = 200;
let offsetLeft = 700;
let cardWidth = 200;
let cardHeight = 300;
let gap = 40;
let numberSize = 50;
const ease = "sine.inOut";

// ========= init 函数 ==========
function init() {
  // ==========================================================
  // 1. 准备阶段 (瞬间完成，没有动画)
  // ==========================================================
  // 确定角色
  const [active, ...rest] = order;
  // order 是 [0, 1, 2, 3, 4, 5]
  // => active = 0 (主角：当前要全屏显示的卡片)
  // => rest = [1, 2, 3, 4, 5] (配角：右侧堆叠的小卡片)

  const detailsActive = detailsEven ? "#details-even" : "#details-odd";
  // detailsEven 是 true
  // => detailsActive = "#details-even" (主角：当前要显示的文字区域)

  const detailsInactive = detailsEven ? "#details-odd" : "#details-even";
  // => detailsInactive = "#details-odd" (替补：下一个要用的文字区域)

  // 计算屏幕宽度
  const { innerHeight: height, innerWidth: width } = window;
  offsetTop = height - 430;
  offsetLeft = width - 830;

  // 设置分页器和导航栏初始位置(使用 gsap.set 进行瞬间移动)
  // 把分页器和导航栏藏在屏幕外面，并且设置为透明
  gsap.set("#pagination", {
    top: offsetTop + 330,
    left: offsetLeft,
    y: 200,
    opacity: 0,
    zIndex: 60,
  });
  gsap.set("nav", { y: -200, opacity: 0 });

  // 把主角卡片(0号)直接设置为全屏大小
  gsap.set(getCard(active), {
    x: 0,
    y: 0,
    width: window.innerWidth,
    height: window.innerHeight,
  });

  // 主角卡片的内容先藏起来
  gsap.set(getCardContent(active), { x: 0, y: 0, opacity: 0 });

  // 把主角文字区域藏在左边屏幕外，替补文字区域也藏起来
  gsap.set(detailsActive, { opacity: 0, zIndex: 22, x: -200 });
  gsap.set(detailsInactive, { opacity: 0, zIndex: 12 });

  // 把替补文字区域里的每一行文字都往下移动一段距离，为后面的入场动画做准备
  gsap.set(`${detailsInactive} .text`, { y: 100 });
  gsap.set(`${detailsInactive} .title-1`, { y: 100 });
  gsap.set(`${detailsInactive} .title-2`, { y: 100 });
  gsap.set(`${detailsInactive} .desc`, { y: 50 });
  gsap.set(`${detailsInactive} .cta`, { y: 60 });

  // 设置底部那个细长的黄色进度条的宽度
  gsap.set(".progress-sub-foreground", {
    width: 500 * (1 / order.length) * (active + 1),
  });

  // 把剩余卡片(1-5号)放到右侧屏幕外的一个起始位置
  rest.forEach((i, index) => {
    gsap.set(getCard(i), {
      x: offsetLeft + 400 + index * (cardWidth + gap),
      y: offsetTop,
      width: cardWidth,
      height: cardHeight,
      zIndex: 30,
      borderRadius: 10,
    });
    gsap.set(getCardContent(i), {
      x: offsetLeft + 400 + index * (cardWidth + gap),
      zIndex: 40,
      y: offsetTop + cardHeight - 100,
    });
    gsap.set(getSliderItem(i), { x: (index + 1) * numberSize });
  });

  // 把顶部黄色进度条藏在左边屏幕外
  gsap.set(".indicator", { x: -window.innerWidth });

  // ==========================================================
  // 2. 表演阶段 (有持续时间，产生动画)
  // ==========================================================
  const startDelay = 0.6; // 所有动画延迟 0.6 秒开始，给页面一点缓冲时间

  // (2.1) 大幕拉开！白色遮罩层从左到右滑出屏幕
  gsap.to(".cover", {
    x: width + 400, // 移动到屏幕右侧外面
    delay: 0.5,
    ease,
    onComplete: () => {
      // 当遮罩层动画完成时，等半秒钟，然后启动自动轮播
      setTimeout(() => {
        loop();
      }, 500);
    },
  });

  // (2.2) 配角入场！所有小卡片从屏幕外滑入到它们最终的堆叠位置
  rest.forEach((i, index) => {
    gsap.to(getCard(i), {
      x: offsetLeft + index * (cardWidth + gap),
      zIndex: 30,
      delay: 0.05 * index,
      ease,
      delay: startDelay,
    });
    gsap.to(getCardContent(i), {
      x: offsetLeft + index * (cardWidth + gap),
      zIndex: 40,
      delay: 0.05 * index,
      ease,
      delay: startDelay,
    });
  });

  // (2.3) UI元素入场！
  // 分页器从下往上滑入
  gsap.to("#pagination", { y: 0, opacity: 1, ease, delay: startDelay });
  // 导航栏从上往下滑入
  gsap.to("nav", { y: 0, opacity: 1, ease, delay: startDelay });
  // 主角文字区域从左侧滑入
  gsap.to(detailsActive, { opacity: 1, x: 0, ease, delay: startDelay });
}

// ======== step 函数 ==========
let clicks = 0;
function step() {
  return new Promise((resolve) => {
    // ==========================================================
    // 一: 更新状态，
    // ==========================================================
    // order.shift(): 会移除数组的第一个元素（比如 0）并返回它。order 变为 [1, 2, 3, 4, 5]
    // order.push(...): 会把刚刚移除的元素 0 添加到数组的末尾。
    order.push(order.shift());

    // 控制文字区域交替的“开关”。
    detailsEven = !detailsEven;
    const detailsActive = detailsEven ? "#details-even" : "#details-odd";
    const detailsInactive = detailsEven ? "#details-odd" : "#details-even";

    // ==========================================================
    // 二: 左侧文字区域的优雅交替
    // ======================================================
    // (2.1) 填充新内容
    document.querySelector(`${detailsActive} .place-box .text`).textContent =
      data[order[0]].place;
    document.querySelector(`${detailsActive} .title-1`).textContent =
      data[order[0]].title;
    document.querySelector(`${detailsActive} .title-2`).textContent =
      data[order[0]].title2;
    document.querySelector(`${detailsActive} .desc`).textContent =
      data[order[0]].description;

    // (2.2) 新文字入场，旧文字退场
    gsap.set(detailsActive, { zIndex: 22 }); //瞬间提升新文字区域的 z-index，让它显示在最上层
    gsap.to(detailsActive, { opacity: 1, delay: 0.4, ease }); //让新的文字区域整体淡入
    //  让区域内的各个文本元素从 y 轴下方滑入到正常位置 (y:0)，通过不同的 delay 创造层次感
    gsap.to(`${detailsActive} .text`, {
      y: 0,
      delay: 0.1,
      duration: 0.7,
      ease,
    });
    gsap.to(`${detailsActive} .title-1`, {
      y: 0,
      delay: 0.15,
      duration: 0.7,
      ease,
    });
    gsap.to(`${detailsActive} .title-2`, {
      y: 0,
      delay: 0.15,
      duration: 0.7,
      ease,
    });
    gsap.to(`${detailsActive} .desc`, {
      y: 0,
      delay: 0.3,
      duration: 0.4,
      ease,
    });
    gsap.to(`${detailsActive} .cta`, {
      y: 0,
      delay: 0.35,
      duration: 0.4,
      onComplete: resolve, //在最后一个动画完成时，调用 resolve，通知 step 函数的调用者动画已完成
      ease,
    });

    // 确保新的文字容器在旧的之上。
    gsap.set(detailsInactive, { zIndex: 12 });

    // 更新 order 识别卡片角色并准备动画
    const [active, ...rest] = order;
    const prv = rest[rest.length - 1]; // 上一个 active

    //  瞬间设置新旧主角的 `z-index`，确保新主角（20）在旧主角（10）之上。
    gsap.set(getCard(prv), { zIndex: 10 });
    gsap.set(getCard(active), { zIndex: 20 });
    // 给旧主角一个轻微放大的“退场特效”，增加视觉动态。
    gsap.to(getCard(prv), { scale: 1.5, ease });

    // 指示器和新主角卡片内容的动画
    gsap.to(getCardContent(active), {
      // 当新主角卡片开始放大时，让它上面的小字内容向上移动并淡出消失。
      y: offsetTop + cardHeight - 10,
      opacity: 0,
      duration: 0.3,
      ease,
    });
    //新的页码滑入
    gsap.to(getSliderItem(active), { x: 0, ease });
    // 旧的页码滑出
    gsap.to(getSliderItem(prv), { x: -numberSize, ease });
    // 进度条平滑增长到新的宽度
    gsap.to(".progress-sub-foreground", {
      width: 500 * (1 / order.length) * (active + 1),
      ease,
    });

    // 新主角卡片的登场动画与收尾工作
    gsap.to(getCard(active), {
      //新主角卡片 active 从它在右侧队列的位置，移动并放大到全屏。
      x: 0,
      y: 0,
      ease,
      width: window.innerWidth,
      height: window.innerHeight,
      borderRadius: 0,
      onComplete: () => {
        const xNew = offsetLeft + (rest.length - 1) * (cardWidth + gap);
        //瞬间将旧主角 prv 缩小并“传送”到右侧队列的末尾。
        gsap.set(getCard(prv), {
          x: xNew,
          y: offsetTop,
          width: cardWidth,
          height: cardHeight,
          zIndex: 30,
          borderRadius: 10,
          scale: 1,
        });

        // 把它对应的内容也传送到队尾并显示出来
        gsap.set(getCardContent(prv), {
          x: xNew,
          y: offsetTop + cardHeight - 100,
          opacity: 1,
          zIndex: 40,
        });

        // 把它对应的页码也传送到队尾
        gsap.set(getSliderItem(prv), { x: rest.length * numberSize });

        // 将已完全隐藏的旧文字区域的内容重置到“出场前”的位置，为下下次使用做准备
        gsap.set(detailsInactive, { opacity: 0 });
        gsap.set(`${detailsInactive} .text`, { y: 100 });
        gsap.set(`${detailsInactive} .title-1`, { y: 100 });
        gsap.set(`${detailsInactive} .title-2`, { y: 100 });
        gsap.set(`${detailsInactive} .desc`, { y: 50 });
        gsap.set(`${detailsInactive} .cta`, { y: 60 });
        clicks -= 1;
        if (clicks > 0) {
          step();
        }
      },
    });

    // 遍历新的配角队列 rest，将它们依次移动到正确的位置
    rest.forEach((i, index) => {
      if (i !== prv) {
        const xNew = offsetLeft + index * (cardWidth + gap);
        gsap.set(getCard(i), { zIndex: 30 });
        // 为每一个需要补位的配角卡片创建一个动画，让它们平滑地移动到新的、
        // 更靠前的位置。通过 delay: 0.1 * (index + 1) 制造出漂亮的级联效果。
        gsap.to(getCard(i), {
          x: xNew,
          y: offsetTop,
          width: cardWidth,
          height: cardHeight,
          ease,
          delay: 0.1 * (index + 1),
        });

        gsap.to(getCardContent(i), {
          x: xNew,
          y: offsetTop + cardHeight - 100,
          opacity: 1,
          zIndex: 40,
          ease,
          delay: 0.1 * (index + 1),
        });
        gsap.to(getSliderItem(i), { x: (index + 1) * numberSize, ease });
      }
    });
  });
}

let autoPlayEnabled = true; // 新增一个全局开关
// ========= loop 函数 ==========
async function loop() {
  if (!autoPlayEnabled) {
    return; // 如果开关是关的，直接退出循环
  }
  // 1. 节拍第一阶段：进度条入场
  await animate(".indicator", 2, { x: 0 });
  // 2. 节拍第二阶段：进度条退场
  await animate(".indicator", 0.8, { x: window.innerWidth, delay: 0.3 });
  // 3. 幕间重置
  gsap.set(".indicator", { x: -window.innerWidth });

  // 在调用 step 前再次检查，防止在进度条播放时用户点击了按钮
  if (!autoPlayEnabled) {
    return;
  }
  // 4. 奏响核心乐章
  await step();
  // 5. 开启下一次循环 (递归调用)
  loop();
}

async function loadImage(src) {
  return new Promise((resolve, reject) => {
    let img = new Image();
    img.onload = () => resolve(img);
    img.onerror = reject;
    img.src = src;
  });
}

async function loadImages() {
  const promises = data.map(({ image }) => loadImage(image));
  return Promise.all(promises);
}

async function start() {
  try {
    await loadImages();
    init();
  } catch (error) {
    console.error("One or more images failed to load", error);
  }
}

start();

// ==========================================================
// 新增：箭头点击切换功能
// ==========================================================

// 1. 创建一个变量作为“动画锁”
let isAnimating = false;

// 2. 获取左右箭头元素
const leftArrow = document.querySelector(".arrow-left");
const rightArrow = document.querySelector(".arrow-right");

// 3. 封装一个统一的切换函数
async function handleArrowClick() {
  // 如果用户点击了，就关掉自动播放开关
  autoPlayEnabled = false;
  // (3.1) 检查动画锁，如果正在播放动画，则直接返回，不执行任何操作
  if (isAnimating) {
    return;
  }

  // (3.2) 立刻上锁，防止连续点击
  isAnimating = true;

  // (3.3) 调用 step() 函数，并等待它完成
  // step() 返回的 Promise 在文字动画结束时 resolve
  await step();

  // (3.4) 动画完成后，开锁，允许下一次点击
  // 我们加一个短暂的延迟，让动画有更完整的收尾时间，手感更好
  setTimeout(() => {
    isAnimating = false;
  }, 500); // 延迟 500 毫秒
}

// 4. 为右箭头（下一张）绑定点击事件
rightArrow.addEventListener("click", () => {
  // 注意：我们在这里不直接调用 handleArrowClick
  // 而是先取消自动播放，然后再手动切换

  // 停止并重启自动播放的逻辑比较复杂，
  // 一个简单的处理方式是直接调用切换函数
  handleArrowClick();
});

// 5. 为左箭头（上一张）绑定点击事件 (高级功能)
leftArrow.addEventListener("click", () => {
  // "上一张" 的功能比 "下一张" 复杂很多，
  // 因为它需要反转整个 step() 函数的逻辑。
  // 对于初学者，我们暂时只实现 "下一张" 的功能，
  // 所以这里可以先留空或只打印信息。
  console.log("向左切换功能待实现");

  // 如果你想挑战，可以尝试编写一个 reverseStep() 函数。
});
