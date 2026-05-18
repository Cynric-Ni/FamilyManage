<template>
  <div class="login-page">
    <section id="login-form">
      <h1>WELCOME</h1>
      <div class="input-wrap">
        <input type="text" placeholder="Username" spellcheck="false" required />
        <font-awesome-icon class="input-icon" icon="fa-solid fa-user" />
      </div>

      <div class="input-wrap">
        <input
          type="password"
          placeholder="Password"
          spellcheck="false"
          required
        />
        <font-awesome-icon class="input-icon" icon="fa-solid fa-lock" />
      </div>

      <button>Login</button>
    </section>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped lang="css">
@import url("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");

* {
  margin: 0;
  padding: 0;
  outline: none;
  border: none;
  font-family: poppins;
}

.login-page {
  display: flex;
  width: 100%;
  min-height: 100vh;
  background-image: url(../assets/login.jpg);
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  justify-content: center;
  align-items: center;
}

#login-form {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  width: 35%;
  padding: 50px 10px;
  background-color: rgba(253, 251, 251, 0.103);
  border-radius: 10px;
  border: 1px solid rgba(255, 254, 254, 0.411);
}

h1 {
  font-size: 2.5rem;
  color: white;
  margin: 0 0 50px;
}

.input-wrap {
  border: 1px solid white;
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 40px;
  height: 60px;
  width: 90%;
  border-radius: 50px;
}

input {
  flex: 1;
  background-color: transparent;
  font-size: 1.2rem;
  color: white;
  padding: 5px 25px;
  min-width: 0;
}

input::placeholder {
  font-size: 1.2rem;
  color: white;
}

.input-icon {
  color: white;
  font-size: 1.2rem;
  padding-right: 25px;
  flex-shrink: 0;
}

button {
  width: 90%;
  height: 60px;
  border-radius: 50px;
  font-size: 1.2rem;
  font-weight: 600;
  cursor: pointer;
  margin-bottom: 25px;
  transition: 0.3s;
}
</style>
