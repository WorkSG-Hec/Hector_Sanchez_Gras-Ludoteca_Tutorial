import { Directive } from "@angular/core";
import { AbstractControl, NG_VALIDATORS, ValidationErrors, Validator, ValidatorFn } from "@angular/forms";

export function noWhitespaceValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
        const value = control.value || '';
        const isWhitespace = value.trim().length === 0;
        return isWhitespace ? { 'whitespace': true } : null;
    };
}

@Directive({
    selector: '[noWhitespace]',
    providers: [
        { provide: NG_VALIDATORS, useExisting: NoWhitespaceValidatorDirective, multi: true }
    ]
})
export class NoWhitespaceValidatorDirective implements Validator {
    validate(control: AbstractControl): ValidationErrors | null {
        return noWhitespaceValidator()(control);
    }
}
