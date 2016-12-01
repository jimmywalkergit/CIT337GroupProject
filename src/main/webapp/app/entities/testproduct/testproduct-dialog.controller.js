(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('TestproductDialogController', TestproductDialogController);

    TestproductDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Testproduct'];

    function TestproductDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Testproduct) {
        var vm = this;

        vm.testproduct = entity;
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
            if (vm.testproduct.id !== null) {
                Testproduct.update(vm.testproduct, onSaveSuccess, onSaveError);
            } else {
                Testproduct.save(vm.testproduct, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cit337ProjectApp:testproductUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
