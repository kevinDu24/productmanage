/**
 * Created by LEO on 16/11/23.
 */
app.controller('appPublishController',['toaster', '$state', '$http', '$scope','$rootScope', '$cookies', function(toaster, $state,$http,$scope,$rootScope, $cookies) {
    $scope.client = {};
    if($cookies.get('publishUserInfo') == null || $cookies.get('publishUserInfo') == ''){
        $state.go('app.publishSignin');
        return;
    }
    $scope.appTypes = [{name:'安卓', type:0}, {name:'苹果', type:1}];
//    $scope.appNames = [{name:'太盟宝', flag:'0', customer: "taimeng"}, {name:'亚驰宝', flag:'1', customer: 'yachi'}, {name:'寻车宝', flag:'2', customer: 'leaduxunche'}];
    $scope.appType = $scope.appTypes[0];
//    $scope.appName = $scope.appNames[0];
    $scope.client.mustUpdate = 'false';
    $scope.toaster = {
        type: 'success',
        title: 'Title',
        text: 'Message'
    };
    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };
    var getAppInfo = function(){
       $http.get('/appVersions/getAppInfo')
               .success(function (result) {
                   if(result.status == 'SUCCESS'){
                      $scope.appNames = result.data;
                       $scope.appName = $scope.appNames[0];
                   }else{
                       $scope.alert(result.error);
                   }
               }).error(function () {
               $scope.alert("系统异常");
           });
        };
    getAppInfo();
    $scope.changeClientType = function(){
        $scope.client.downloadUrl = null;
    };

    $scope.upload = function(){
        var file = new FormData();
        file.append('file', $scope.file);
        $http.post('/files?type=apkUpload', file, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined, 'Header-Param' : '{\"systemflag\":\"'+ $scope.appName.customer  +'\"}'}
        }).success(function(result){
            if(result.status == 'SUCCESS'){
                $scope.client.downloadUrl = result.data.url;
                $scope.pop('success', '', '上传成功');
            }else{
                $scope.error = result.error;
            }
        });
    };

    $scope.publish = function(){
        if($scope.client.downloadUrl == null){
            var msg = null;
            if($scope.appType.type == 0){
                 msg = '请上传最新版客户端安装包';
                 $scope.pop('error', '', msg);
                 return;
            }else{
                 msg = '请填写url';
                 $scope.pop('error', '', msg);
                 return;
            }
        }
        $scope.client.type = $scope.appType.type;
        $scope.client.appName = $scope.appName.name;
        $scope.client.appFlag = $scope.appName.appFlag
        var headers ={'Header-Param' : '{\"systemflag\":\"'+ $scope.appName.customer  +'\"}'};
        $http.post('/appVersions', $scope.client).success(function(result){

            if(result.status == 'SUCCESS'){
                $scope.pop('success', '', '新版本发布成功');
            }else{
                $scope.pop('error', '', result.error);
            }
        });
    }
}]);