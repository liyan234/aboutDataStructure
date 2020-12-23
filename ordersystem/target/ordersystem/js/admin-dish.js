var app = new Vue({
    el: '.center',
    data: {
        // 当前登录用户的用户名, 为空字符串表示未登录状态
        name: "",
        // 是否显示新增菜品对话框
        showAddDishDialog: false,
        // 新增菜品的信息
        newDish: {
            name: "",
            price: 0,
        },
        // 当前的菜品列表
        dishes: [

        ],
    },
    methods: {
        // 判定当前是否登录
        isLogin() {
            if (this.name == "") {
                return false;
            }
            return true;
        },
        // 从服务器检测登录状态
        checkLogin() {
            // 页面最初加载时先访问服务器判定自身的登陆状态.
            console.log("checkLogin");
            $.ajax({
                url: 'login',
                type: 'get',
                success: function (data, status) {
                    if (data.ok != 1) {
                        return;
                    }
                    // 登录成功后
                    // 1. 设置用户名
                    app.name = data.name;
                    // 2. 获取菜品列表
                    app.getDishes();
                }
            });
        },
        // 获取菜品信息
        getDishes() {
            // 获取菜品信息
            console.log("getDishes");
            $.ajax({
                url: 'dish',
                type: 'get',
                success: function (data, status) {
                    app.dishes = data;
                }
            })
        },
        deleteDish(dishId) {
            console.log("deleteDish");
            $.ajax({
                type: 'delete',
                url: 'dish?dishId=' + dishId,
                success: function (data, status) {
                    app.getDishes();
                    alert("删除菜品成功!");
                    app.showAddDishDialog = false;
                    app.newDish = {};
                }
            })
        },
        addDish() {
            console.log("addDish");
            $.ajax({
                type: 'post',
                url: 'dish',
                data: JSON.stringify(app.newDish),
                success: function (data, status) {
                    if (data.ok != 1) {
                        alert("新增菜品失败! " + data.reason);
                        return;
                    }
                    app.getDishes();
                    alert("新增菜品成功!");
                    app.showAddDishDialog = false;
                    app.newDish = {};
                }
            })
        },
        logout() {
            alert("注销功能还未实现嗷! 少年我看你骨骼清奇, 是编程奇才, 要不自己试试?");
        }
    }
});