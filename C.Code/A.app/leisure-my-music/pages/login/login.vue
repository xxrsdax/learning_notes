<template>
	<view class="login">

		<!--  背景图片	-->
		<image class="image-bg" src="../../static/a.png" />

		<!-- 控制按钮与页面的高度 -->
		<view :style="heightStyle">
		</view>

		<view style="padding:0rpx 48rpx;margin-bottom: 40rpx;"> 
			<button type="default" :class="buttonStyle" @click="login">手机号登陆</button>
		</view>
		
		<view style="padding:0rpx 48rpx;">
			<button :class="[{'buttonClassOut' : flag.register },{'buttonClassIn': !flag.register}]">注册</button>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				//高度样式
				heightStyle: {
					paddingTop: "100rpx"
				},
				//样式标识
				flag: {
					//登录按钮
					login: true,
					//注册按钮
					register: true
				}

			}
		},
		methods: {
 
				buttonStyle: "buttonClassOut"
			}
		},
		methods: {
			//登录 
			login: function() {
				alert("s");
				console.log("页面跳转")
				uni.switchTab({
				    url: '/pages/tabBar/findMusic/findMusic'
				});
				
			}
		},
		mounted() {
			//获取当前手机屏幕尺寸
			const {
				windowWidth,
				windowHeight
			} = uni.getSystemInfoSync();

			//动态修改样式：
			// 	思路一 获取当前元素对象 直接操作器style对象的对应属性
			// 	思路二 设置vue对象 动态绑定style属性

			let buttonClass = document.querySelectorAll(".buttonClass");

			this.heightStyle.paddingTop = (windowHeight / 5) * 4 + "rpx";
			// buttonClass.forEach((item)=>{
			// 	// item.style.marginTop = "100rpx"
			// }) 
			// this.button_Style.top = (windowHeight / 3) +"rpx" 

		}
	}
</script>
<style scoped>
	.login {}

	/* 图片背景样式  */
	.image-bg {
		position: absolute;
		z-index: -1;
		left: 0;
		right: 0;
		bottom: 0;
		right: 0;
		width: 100%;
		height: 100%;
	}

	/* 鼠标在按钮外样式 */
	.buttonClassOut {
		border-radius: 120rpx;
		border: 1rpx solid red;
		background-color: #FFFFFF;
		color: red;
		font-size: 34rpx;
	}

	/* 鼠标在按钮内样式 */
	.buttonClassIn {
		border-radius: 120rpx;
		border: 1rpx solid red;
		background-color: red;
		color: #FFFFFF;
		font-size: 34rpx;
	}
</style>
