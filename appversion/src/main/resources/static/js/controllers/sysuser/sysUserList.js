/**
 * Created by qiaohao on 2016/10/20.
 */
'use strict';

app.controller('sysUserListController', ['$scope', '$http', '$modal', 'toaster', 'moment', function ($scope, $http, $modal, toaster, moment) {
    //ngGrid初始化数据
    $scope.filterOptions = {
        filterText: "",
        useExternalFilter: true
    };

    //提示信息
    $scope.toaster = {
        type: 'success',
        title: 'Title',
        text: 'Message'
    };
    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };

    $scope.pagingOptions = {
        pageSizes: [10, 15, 20],
        pageSize: '10',
        currentPage: 1
    };
    $scope.gridOptions = {
        data: 'codes',
        enablePaging: true,
        showFooter: true,
        rowHeight: 41,
        headerRowHeight: 36,
        multiSelect: false,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        columnDefs: [
            /*{ field: 'id', displayName: '序号' },*/
            { field: 'username', displayName: '用户名', width:'800px' },
            { field: 'remove', displayName: '操作', width: "400px", cellTemplate: '<a ng-click="editRowIndex(row.entity)" title="编辑" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-pencil"></i></a>' +
            '<a mwl-confirm message="确定删除?" title="删除" confirm-text="确定" cancel-text="取消" confirm-button-type="danger" on-confirm="removeRowIndex(row.entity)" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-times"></i></a>' +
            '<a ng-click="editPassword(row.entity)" title="编辑密码" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="icon-key"></i></a>' +
            '<a ng-click="seeRowIndex(row.entity)" title="详情" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-info-circle"></i></a>' }
        ]
    };
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        var url = '/sysusers?page=' + page + '&size=' + pageSize ;
        $http.get(url).success(function (pagedata) {
            $scope.codes = pagedata.data.content;
            $scope.totalServerItems = pagedata.data.totalElements;
        });
    };
    $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, "");
    $scope.$watch('pagingOptions', function (newVal, oldVal) {
        if (newVal !== oldVal || newVal.currentPage !== oldVal.currentPage || newVal.pageSize !== oldVal.pageSize) {
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);

    $scope.$watch('filterOptions', function (newVal, oldVal) {
        if (newVal !== oldVal) {
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);

    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };

    $scope.search = function(){
        $scope.pagingOptions.currentPage = 1;
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, '');
    }


    $scope.createSysUser = function(){
        var rtn = $modal.open({
            templateUrl: 'tpl/sysuser/create_sysuser.html',
            controller: 'createSysUserController',
            resolve:{
            }
        });
        rtn.result.then(function (status) {
            if(status == 'SUCCESS') {
                $scope.pop('success', '', '新增用户信息成功');
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
            }
        },function(){
        });
    }


    $scope.seeRowIndex = function(entity){
        var rtn = $modal.open({
            templateUrl: 'tpl/sysuser/see_sysuser.html',
            controller: 'seeSysUserController',
            resolve:{
                sysUser : function (){ return entity }
            }
        });
        rtn.result.then(function (status) {
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
        },function(){
        });
    }




    $scope.editRowIndex = function(entity){

        if(this.row.entity.username=='admin'){
            $scope.pop('error', '', '管理员信息不能修改');
            return;
        }

        var id = this.row.entity.id;
        var rtn = $modal.open({
            templateUrl: 'tpl/sysuser/update_sysuser.html',
            controller: 'updateSysUserController',
            resolve:{
                sysUserId:function(){return id;}
            }
        });
        rtn.result.then(function (status) {
            if(status == 'SUCCESS') {
                $scope.pop('success', '', '修改用户信息成功');
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
            }
        },function(){
        });
    }





    $scope.editPassword = function(entity){
        var id = this.row.entity.id;
        var rtn = $modal.open({
            templateUrl: 'tpl/sysuser/update_sysuser_password.html',
            controller: 'updateSysUserPasswordController',
            resolve:{
                sysUserId:function(){return id;}
            }
        });
        rtn.result.then(function (status) {
            if(status == 'SUCCESS'){
                $scope.pop('success','','修改用户密码成功');
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
            }

        },function(){
        });
    }








    $scope.removeRowIndex = function(entity){
        if(this.row.entity.username=='admin'){
            $scope.pop('error', '', '管理员信息不能删除');
            return;
        }

        $http.delete('sysusers/'+this.row.entity.id).success(function(data) {
            if(data.status == 'SUCCESS'){
                $scope.pop('success','','删除成功');
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
            }else{
                alert(data.error);
            }
        })
    }

}])
;