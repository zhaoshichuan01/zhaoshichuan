<template>
  <div class="form-container">
    <h2>测试表单页面</h2>
    <form @submit.prevent="submitForm">
      <div class="form-group">
        <label for="name">姓名:</label>
        <input type="text" id="name" v-model="form.name" required />
      </div>
      
      <div class="form-group">
        <label for="email">邮箱:</label>
        <input type="email" id="email" v-model="form.email" required />
      </div>
      
      <div class="form-group">
        <label for="message">留言:</label>
        <textarea id="message" v-model="form.message" rows="4"></textarea>
      </div>
      
      <button type="submit">提交</button>
    </form>
    
    <div class="result" v-if="responseData">
      <h3>从后端获取的数据:</h3>
      <pre>{{ JSON.stringify(responseData, null, 2) }}</pre>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';

export default {
  name: 'TestForm',
  setup() {
    const form = ref({
      name: '',
      email: '',
      message: ''
    });
    
    const responseData = ref(null);
    
    // 提交表单
    const submitForm = async () => {
      try {
        const response = await fetch('/api/submit-form', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(form.value)
        });
        
        const result = await response.json();
        console.log('表单提交成功:', result);
        alert('表单提交成功!');
      } catch (error) {
        console.error('表单提交失败:', error);
        alert('表单提交失败: ' + error.message);
      }
    };
    
    // 获取后端数据
    const fetchData = async () => {
      try {
        const response = await fetch('/api/test-data');
        const data = await response.json();
        responseData.value = data;
      } catch (error) {
        console.error('获取数据失败:', error);
      }
    };
    
    // 组件挂载时获取数据
    onMounted(() => {
      fetchData();
    });
    
    return {
      form,
      responseData,
      submitForm
    };
  }
};
</script>

<style scoped>
.form-container {
  max-width: 600px;
  margin: 20px auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input,
textarea {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

button {
  background-color: #4CAF50;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}

.result {
  margin-top: 20px;
  padding: 15px;
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 4px;
}
</style>