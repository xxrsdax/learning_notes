

# 1.使用博客



## lrz的使用

![img](img/original.png)

[清风挽离人](https://me.csdn.net/qq_37190789) 2019-11-14 23:59:30 ![img](img/articleReadEyes.png) 1195 ![img](img/tobarCollect.png) 收藏 2

分类专栏： [前端](https://blog.csdn.net/qq_37190789/category_8629937.html) [Vue](https://blog.csdn.net/qq_37190789/category_9066908.html)

版权

在最近的一个物业管理的项目中，用户上传图片的时，需要对图片的大小进行限制，于是使用了未曾接触到的lrz插件对用户上传的图片进行压缩。关于lrz插件的使用有关以下记录。

### **1.如何使用**

使用 npm 进行下载

```
cnpm instll lrz  --save
```

在main.js中引入

```
import lrz from 'lrz'
```

用户进行图片上传时，需要用一个input fire 来获取图片。

```
 <input id="file" type="file" accept="image/*" ref='file' @change="getImg($event)"/>
```

lrz(fire,[option])l

### **参数：**

其中fire你所上传的图片的二进制流。
option为可选参数，具体参考git地址[lrz](https://www.npmjs.com/package/lrz)
其结果返回一个promise对象，then(res)

res.fileLen 是压缩后的图片大小，可以通过这个的值传递给后端，判断是否上传完整；
res.base64 生成后的图片base64，后端可以处理此字符串为图片，也直接用于img.src = base64；
res.base64Len 生成后的base64的大小，后端可以通过此值来校验是否传输完整 (如果采用base64上传方式)；
res.origin 为原始图片的一些数据信息，例如：图片名称，图片的大小，图片的类型等等都保存在这个里面；

**例：**

```
<template>
    <div class="index">
        <!-- 图片上传 -->
        <div class="img">
            <input id="file" type="file" accept="image/*" ref='file' @change="getImg($event)"/>
            <span class="close" v-show='state' @click="close">x</span>
            <img :src="img" alt="" v-show='state'>
        </div>
    </div>
</template>
<script>
export default {
    data(){
        return{
            state:false,
            img:''
        }
    },
    components: {
        
    },
    methods: {
        getImg(e){
            lrz(e.target.files[0],'').then(res=>{
                this.img=res.base64;
                this.state=true;
                console.log(res)
            })
        },
        close(){
            this.state=false;  //关闭显示状态
            this.img='';  //清空图片路径
        }
    }
}
</script>


<style scope>
    .img{
        width: 8rem;
        height: 8rem;
        border: 1px solid #ccc;
        background: url('../assets/pp.png') no-repeat;
        background-size: 50%;
        background-position: center;
        position: relative;
    }
    .img input{
        width: 100%;
        height: 100%;
        display: block;
        opacity: 0;
        border: none;
       
    }
    .img .close{
        position: absolute;
        width: 20px;
        height: 20px;
        top: -10px;
        right: -10px;
        display: block;
        border-radius: 50%;
        background: #ccc;
        color: #fff;
        z-index: 3;
    }
    .img img{
        position: absolute;
        width: 100%;
        height: 100%;
        z-index: 2;
        left: 0;
        top: 0;
    }
</style>

```

由于缺少一些图片素材，最终效果不够美观。
![在这里插入图片描述](img/20191114235714116.gif)

次文仅作为笔记方便以后查看，不喜勿喷，如果还有什么插件，希望各位不吝分享，共同进步





# 2.博客评论

```

lrz(file,{
  width:800,  //设置图片压缩后的最大宽度，默认为原图宽度
  height:600,  //同上
  quality:0.7,  //图片压缩质量，取值 0 - 1，默认为 0.7
  fieldName: "aijquery" //后端接收的字段名，默认：file,一般不用这项，我们要上传数据的话，可以自定义FormData对象   
}).then( function (rst){
  rst.formData //后端可处理的数据
  rst.file   //压缩后的file对象，如果压缩率太低，将会是原始file对象
  rst.fileLen //压缩后的图片的大小，
  rst.base64   //生成后的图片base64，后端可以处理此字符串为图片，也可以直接用于 img.src = base64
  rst.base64Len   //生成后的base64的大小，后端可以通过此值来校验是否传输完整
  rst.origin //原始的file对象，里面存放了一些原始文件的信息，例如大小、日期等
}).catch(function(err){  //处理失败后执行

}).always( function (){  //必然执行

});
摘自 爱jQuery：http: //www.aijquery.cn/Html/jquerychajian/194.html
```

