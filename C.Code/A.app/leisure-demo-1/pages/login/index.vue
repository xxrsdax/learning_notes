<template>
	<view class="login">

		<view class="head">
			<text>
				欢迎登录闲
			</text>
		</view>

		<view class="body">
			<form @submit="formSubmit" @reset="formReset">
				<view  class="login_border" >
					<input class="login_input" name="nickname" placeholder="请输入用户名" />
				</view>
				<view class="login_border" >
					<input class="login_input" name="nicknameTwo" placeholder="请输入密码" />
				</view>
				<view class="login_border" >
					<input class="login_input" name="nicknameThree" placeholder="请输入验证码" />
				</view>
				
				<view class="login_border ">
				    <input class="login_input" placeholder="请输入密码" :password="showPassword" />
				    <text class="uni-icon" :class="[!showPassword ? 'uni-eye-active' : '']" @click="changePassword">&#xe568;</text>
				</view>
				
				<view >
					<button form-type="submit">登录</button>
					<button type="default" form-type="reset" ><text style="color:#007AFF">忘记密码</text></button>
				</view>
				
			</form>
		</view>

	</view>
</template>

<script>
	var graceChecker = require("../../common/graceChecker.js");
	export default {
		data() {
			return {
				showPassword:true
			}
		},
		methods: {
			formSubmit: function(e) {
				console.log('form发生了submit事件，携带数据为：' + JSON.stringify(e.detail.value))
				//定义表单规则
				var rule = [{
						name: "nickname",
						checkType: "string",
						checkRule: "1,3",
						errorMsg: "姓名应为1-3个字符"
					},
					{
						name: "gender",
						checkType: "in",
						checkRule: "男,女",
						errorMsg: "请选择性别"
					},
					{
						name: "loves",
						checkType: "notnull",
						checkRule: "",
						errorMsg: "请选择爱好"
					}];
				//进行表单检查
				var formData = e.detail.value;
				var checkRes = graceChecker.check(formData, rule);
				if (checkRes) {
					uni.showToast({
						title: "验证通过!",
						icon: "none"
					});
				} else {
					uni.showToast({
						title: graceChecker.error,
						icon: "none"
					});
				}
			},
			formReset: function(e) {
				console.log('清空数据')
			},
			//更改密码
			changePassword(){
				this.showPassword = !this.showPassword;
			}
			
		}
	}
</script>

<style scoped>
	.uni-form-item .title {
		padding: 20rpx 0;
	}

	/*  整体   */
	.login {
		position: fixed;
		top: 0;
		bottom: 0;
		left: 0;
		right: 0;
		background: #F3F3F3;
		/* z-index: -1; */


	}

	/* 标题  */
	.head {
		text-align: center;
		padding-top: 100rpx;
		padding-bottom: 15rpx;
	}

	/* 内容 */
	.body {
		padding: 10rpx 32rpx;
	}

	/* 输入外框 */
	.login_border{
		padding: 16rpx 4rpx;
		background-color:#FFFFFF;
		margin-bottom: 40rpx;
	}
	
	/* 输入框 */
	.login_input {
		margin-top: 10rpx;
		margin-bottom: 10rpx;
	}
</style>
