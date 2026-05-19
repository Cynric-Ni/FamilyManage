<template>
  <div class="login-page">
    <section id="login-form">
      <h1>家庭物品管理</h1>
      <div class="input-wrap in-1">
        <input type="text" placeholder="Username" spellcheck="false" required />
        <font-awesome-icon class="input-icon" icon="fa-solid fa-user" />
      </div>

      <div class="input-wrap in-2">
        <input
          type="password"
          placeholder="Password"
          spellcheck="false"
          required
        />
        <font-awesome-icon class="input-icon" icon="fa-solid fa-lock" />
      </div>
      <div class="rem">
        <p>
          <input type="checkbox" />
          Remember me
        </p>
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
  background-image: url(../assets/image/login.jpg);
  background-repeat: no-repeat;
  background-size: 100% 100%;
  background-position: center;
  justify-content: center;
  align-items: center;
}

.login-page::after {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.2);
  z-index: -1;
}

#login-form {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  width: 30%;
  padding: 50px 10px;
  background-color: rgba(253, 251, 251, 0.103);
  border-radius: 10px;
  border: 1px solid rgba(255, 254, 254, 0.411);
  backdrop-filter: blur(10px);
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.13);
  overflow: hidden;
}

h1 {
  font-size: 3rem;
  color: white;
  margin: 0 0 25px;
  letter-spacing: 3px;

  animation: reloadA 1s ease-out forwards;
  opacity: 0;
  animation-delay: 0.2s;
}

.in-1{
    animation: reloadA 1.2s ease-out forwards;
    opacity: 0;
    animation-delay: 0.4s;
}

.in-2{
    animation: reloadA 1.4s ease-out forwards;
    opacity: 0;
    animation-delay: 0.6s;
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
  box-shadow: 0 0 5px rgba(255, 255, 255, 0.753);
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

.rem {
  width: 90%;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin-bottom: 25px;

  animation: reloadA 1.6s ease-out forwards;
  opacity: 0;
  animation-delay: 0.8s;
}

.rem p {
  color: white;
  font-size: 1rem;
  display: flex;
  align-items: center;
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

  animation: reloadA 1.8s ease-out forwards;
  opacity: 0;
  animation-delay: 1s;
}

button:hover {
  background-color: rgb(180, 180, 180);
}

@keyframes reloadA{
  from{
    transform: translateY(250px);
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}
</style>
