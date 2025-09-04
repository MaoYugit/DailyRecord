// 1. 获取页面上的元素
const taskInput = document.getElementById('task-input');
const addBtn = document.getElementById('add-btn');
const taskList = document.getElementById('task-list');


// 2. 绑定事件监听器
// 当添加按钮被点击， 或者在输入框中按下回车键时， 添加任务
addBtn.addEventListener('click', addTask);
taskInput.addEventListener('keypress', function(event) {
    if (event.key === 'Enter') {
        addTask();
    }
})

// 3. 定义添加任务的函数
function addTask() {
    // 获取输入框中的任务内容 并去除首尾空格
    const taskText = taskInput.value.trim();

    // 如果任务内容为空， 则不添加任务
    if (taskText === '') {
        alert('任务内容不能为空！');
        return;
    }
    // 创建一个新的列表项元素
    const li = document.createElement('li');
    li.textContent = taskText;
}