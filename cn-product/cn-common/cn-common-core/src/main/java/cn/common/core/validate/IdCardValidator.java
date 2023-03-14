package cn.common.core.validate;

import cn.hutool.core.util.IdcardUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdCardValidator implements ConstraintValidator<IdCard, Object> {


	private boolean nullAllowed;
	/**
	 * 是否是身份证
	 * @return
	 */
	private boolean isIdentityCard;

	/**
	 * Initializes the validator in preparation for
	 * {@link #isValid(Object, ConstraintValidatorContext)} calls.
	 * The constraint annotation for a given constraint declaration
	 * is passed.
	 * <p>
	 * This method is guaranteed to be called before any use of this instance for
	 * validation.
	 * <p>
	 * The default implementation is a no-op.
	 *
	 * @param constraintAnnotation annotation instance for a given constraint declaration
	 */
	@Override
	public void initialize(IdCard constraintAnnotation) {
		this.isIdentityCard = constraintAnnotation.isIdentityCard();
		this.nullAllowed = constraintAnnotation.nullAllowed();
	}

	/**
	 * Implements the validation logic.
	 * The state of {@code value} must not be altered.
	 * <p>
	 * This method can be accessed concurrently, thread-safety must be ensured
	 * by the implementation.
	 *
	 * @param value   object to validate
	 * @param context context in which the constraint is evaluated
	 * @return {@code false} if {@code value} does not pass the constraint
	 */
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {


		//身份证号为null时,校验通过
		if(value ==null){
			return nullAllowed;
		}
		String idCard = value.toString();
		if(!idCard.matches("^[0-9A-Z_]+$")){
			return false;
		}
		if(isIdentityCard){
			return IdcardUtil.isValidCard(idCard);
		}
		return true;
	}
}
