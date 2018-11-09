app.controller('updateSysUserController', ['$scope', '$http', '$modalInstance', 'sysUserId', function ($scope, $http, $modalInstance,sysUserId) {



    function init(){

        $http.get('sysroles/getAllSysRole').success(function (data){
            $scope.roles = data.data;
        });

        $http.get('sysusers/'+ sysUserId).success(function(data){
            $scope.sysUser = data.data;
        });

    }

    init();

    /**
     * 保存角色
     */
    $scope.update = function () {
        $http.put('sysusers', $scope.sysUser).success(function (data) {
            $scope.close('SUCCESS');
        })
    }

    /**
     * 关闭新增窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };
}]);
