'use strict';

/* Controllers */
  // signin controller
app.controller('appPublishSigninController',["toaster",'$rootScope', '$scope', '$http', '$state', '$localStorage','$cookieStore', '$cookies', function (toaster,$rootScope, $scope, $http, $state,$localStorage,$cookieStore,$cookies) {

    $scope.user = {};
    $scope.alerts = [];
    //提示信息
    $scope.toaster = {
        type: 'success',
        title: 'Title',
        text: 'Message'
    };
    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };

    var authenticate = function () {
        $scope.alerts = [];
        //$scope.user.password = '';
        $http.get('/system/user?username='+$scope.user.username+'&password='+$scope.user.password)
            .success(function (result) {
                if(result.status == 'SUCCESS'){
                    var expireDate = new Date();
                    expireDate.setDate(expireDate.getDate() + 1);
                    $cookies.put('publishUserInfo', $scope.user.username, {'expires': expireDate});
                    var url = ($localStorage.infoUrl == null || $localStorage.infoUrl == '') ? 'app.publish' : $localStorage.infoUrl;
                    $state.go(url);
                }else{
                    $scope.addAlert("danger",result.error);
                }
            }).error(function () {
            $scope.addAlert("danger","用户名或密码错误！");
        });
    };
    $scope.login = function () {
        $scope.alerts = [];
        if ('' == $scope.user.username||undefined == $scope.user.username) {
            $scope.addAlert("danger","请填写账号");
            return;
        }
        if ('' == $scope.user.password||undefined == $scope.user.password) {
            $scope.addAlert("danger","请填写密码");
            return;
        }
        authenticate();
    };
    $scope.addAlert = function (type,msg) {
        $scope.alerts.push({
            type: type,
            msg: msg
        })
    };
    $scope.closeAlert = function (b) {
        $scope.alerts.splice(b, 1)
    }

       $scope.typeState='password';
        $scope.iconState='fa-eye';
        $scope.hidePassword= function () {
            if($scope.typeState=='password'){
                $scope.typeState='text';
                $scope.iconState='fa-eye-slash';

            }else{
                $scope.typeState='password';
                $scope.iconState='fa-eye';
            }
        }
  }])
;