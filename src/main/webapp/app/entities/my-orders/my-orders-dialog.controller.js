(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('MyOrdersDialogController', MyOrdersDialogController);

    MyOrdersDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MyOrders', 'MyCustomer', 'MyProduct'];

    function MyOrdersDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MyOrders, MyCustomer, MyProduct) {
        var vm = this;

        vm.myOrders = entity;
        vm.clear = clear;
        vm.save = save;
        vm.mycustomers = MyCustomer.query();
        vm.myproducts = MyProduct.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.myOrders.id !== null) {
                MyOrders.update(vm.myOrders, onSaveSuccess, onSaveError);
            } else {
                MyOrders.save(vm.myOrders, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cit337ProjectApp:myOrdersUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
