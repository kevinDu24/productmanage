app.controller('seeSysUserController', ['$scope', '$http', '$modalInstance', 'sysUser', function ($scope, $http, $modalInstance,sysUser) {
    function init(){

        $http.get('sysroles/getAllSysRole').success(function (data){
            $scope.roles = data.data;
        });

        $scope.sysUser = sysUser;
    }
    init();

    /**
     * 关闭新增窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };
}]);


