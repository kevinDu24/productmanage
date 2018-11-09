app.directive('fileUpload', ["FileUploader","$http",function (FileUploader,$http) {
    return {
        restrict: 'EA',
        replace: true,
        required: 'targeturi',
        scope: {
            targeturi: "=",
            onChange: '&',

        },
        templateUrl: 'js/template/single-image-upload-directive.html',
        link: function (scope, elm, attrs) {
            scope.uploader = new FileUploader();
            scope.uploader.filters.push({
                name: 'customFilter',
                fn: function(item /*{File|FileLikeObject}*/, options) {
                    return this.queue.length < 1;
                }
            });
            scope.uploader.filters.push({
                name: 'extension',
                fn: function(item) {
                    return /\/(xls|xlsx)$/.test(item.file.type);
                }
            });
            scope.uploader.onSuccessItem = function(item, response, status, headers) {
                if(response.status == 'ERROR'){
                    item.isSuccess = false;
                    item.isError = true;
                }
                if(item.isError){
                    scope.onChange({type:'error',title:'',text:'导入失败,文件格式错误'});
                    scope.imgBool=false;
                }else{
                    scope.onChange({type:'success',title:'',text:'导入成功'});
                    scope.imgBool=true;
                }
            };
            scope.uploader.onErrorItem = function(item, response, status, headers) {
                console.log(response);
            };
            scope.uploader.url=scope.targeturi;
            scope.$watch("targeturi",function(newVal,oldVal){
                if(newVal!=oldVal){
                    scope.uploader.url=newVal;
                }
            },true)
            scope.fileUpload = function(){
                var temp = scope.uploader.queue.length - 1;
                scope.uploader.queue[temp].upload();
                scope.pop('success','','导入成功');
            }
        }

    };
}]);