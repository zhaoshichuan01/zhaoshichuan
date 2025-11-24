<script setup>
import HelloWorld from './components/HelloWorld.vue'
import TestForm from './view/test.vue'
import { ref } from 'vue'

// 当前页面状态
const currentPage = ref('home')

// 切换到测试页面
const goToTestPage = () => {
  currentPage.value = 'test'
}

// 返回首页
const goHome = () => {
  currentPage.value = 'home'
}
</script>

<template>
  <div>
    <!-- 首页内容 -->
    <div v-if="currentPage === 'home'">
      <div>
        <a href="https://vite.dev" target="_blank">
          <img src="/vite.svg" class="logo" alt="Vite logo" />
        </a>
        <a href="https://vuejs.org/" target="_blank">
          <img src="./assets/vue.svg" class="logo vue" alt="Vue logo" />
        </a>
      </div>
      <HelloWorld msg="Vite + Vue" />
      
      <!-- 添加导航到测试页面的按钮 -->
      <div style="margin-top: 20px; text-align: center;">
        <button 
          @click="goToTestPage" 
          style="padding: 10px 20px; background-color: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer;"
        >
          访问测试表单页面
        </button>
      </div>
    </div>
    
    <!-- 测试表单页面 -->
    <div v-else-if="currentPage === 'test'">
      <div style="margin-bottom: 20px; text-align: center;">
        <button 
          @click="goHome" 
          style="padding: 10px 20px; background-color: #2196F3; color: white; border: none; border-radius: 4px; cursor: pointer;"
        >
          ← 返回首页
        </button>
      </div>
      <TestForm />
    </div>
  </div>
</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>