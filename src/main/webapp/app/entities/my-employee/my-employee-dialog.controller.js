(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('MyEmployeeDialogController', MyEmployeeDialogController);

    MyEmployeeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MyEmployee'];

    function MyEmployeeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MyEmployee) {
        var vm = this;

        vm.myEmployee = entity;
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
            if (vm.myEmployee.id !== null) {
                MyEmployee.update(vm.myEmployee, onSaveSuccess, onSaveError);
            } else {
                MyEmployee.save(vm.myEmployee, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cit337ProjectApp:myEmployeeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
