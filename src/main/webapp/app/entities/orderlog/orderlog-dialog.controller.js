(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('OrderlogDialogController', OrderlogDialogController);

    OrderlogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Orderlog', 'Orderproduct', 'Ordercustomer'];

    function OrderlogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Orderlog, Orderproduct, Ordercustomer) {
        var vm = this;

        vm.orderlog = entity;
        vm.clear = clear;
        vm.save = save;
        vm.orderproducts = Orderproduct.query();
        vm.ordercustomers = Ordercustomer.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.orderlog.id !== null) {
                Orderlog.update(vm.orderlog, onSaveSuccess, onSaveError);
            } else {
                Orderlog.save(vm.orderlog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cit337ProjectApp:orderlogUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
