(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('MyProductDialogController', MyProductDialogController);

    MyProductDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MyProduct', 'MyCart', 'MyOrders'];

    function MyProductDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MyProduct, MyCart, MyOrders) {
        var vm = this;

        vm.myProduct = entity;
        vm.clear = clear;
        vm.save = save;
        vm.mycarts = MyCart.query();
        vm.myorders = MyOrders.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.myProduct.id !== null) {
                MyProduct.update(vm.myProduct, onSaveSuccess, onSaveError);
            } else {
                MyProduct.save(vm.myProduct, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cit337ProjectApp:myProductUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
