(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('OrdercustomerDialogController', OrdercustomerDialogController);

    OrdercustomerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Ordercustomer'];

    function OrdercustomerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Ordercustomer) {
        var vm = this;

        vm.ordercustomer = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ordercustomer.id !== null) {
                Ordercustomer.update(vm.ordercustomer, onSaveSuccess, onSaveError);
            } else {
                Ordercustomer.save(vm.ordercustomer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cit337ProjectApp:ordercustomerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
