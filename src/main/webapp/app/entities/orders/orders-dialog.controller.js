(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('OrdersDialogController', OrdersDialogController);

    OrdersDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Orders', 'Customer', 'Product', 'Employee'];

    function OrdersDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Orders, Customer, Product, Employee) {
        var vm = this;

        vm.orders = entity;
        vm.clear = clear;
        vm.save = save;
        vm.customers = Customer.query();
        vm.products = Product.query();
        vm.employees = Employee.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.orders.id !== null) {
                Orders.update(vm.orders, onSaveSuccess, onSaveError);
            } else {
                Orders.save(vm.orders, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cit337ProjectApp:ordersUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
