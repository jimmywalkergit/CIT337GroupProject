(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('OrderproductDialogController', OrderproductDialogController);

    OrderproductDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Orderproduct'];

    function OrderproductDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Orderproduct) {
        var vm = this;

        vm.orderproduct = entity;
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
            if (vm.orderproduct.id !== null) {
                Orderproduct.update(vm.orderproduct, onSaveSuccess, onSaveError);
            } else {
                Orderproduct.save(vm.orderproduct, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cit337ProjectApp:orderproductUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
