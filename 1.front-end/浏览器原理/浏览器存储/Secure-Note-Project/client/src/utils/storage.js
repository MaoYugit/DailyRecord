// client/src/utils/storage.js

class StorageHandler {
  constructor(strategy = "localStorage") {
    this.storage = window[strategy];
  }

  set(key, value, expire) {
    const data = {
      value,
      time: Date.now(),
      expire,
    };
    try {
      this.storage.setItem(key, JSON.stringify(data));
    } catch (e) {
      console.error("存储失败:", e);
    }
  }

  get(key) {
    const dataString = this.storage.getItem(key);
    if (!dataString) return null;

    try {
      const data = JSON.parse(dataString);
      // 检查过期
      if (data.expire && Date.now() - data.time > data.expire) {
        this.remove(key);
        return null;
      }
      return data.value;
    } catch (e) {
      return null;
    }
  }

  remove(key) {
    this.storage.removeItem(key);
  }

  clear() {
    this.storage.clear();
  }
}

// 导出两个实例，方便直接使用
export const localStore = new StorageHandler("localStorage");
export const sessionStore = new StorageHandler("sessionStorage");
