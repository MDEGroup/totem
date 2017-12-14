/**
 */
package simplefm;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Binary Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link simplefm.BinaryConstraint#getLeft <em>Left</em>}</li>
 *   <li>{@link simplefm.BinaryConstraint#getRight <em>Right</em>}</li>
 * </ul>
 *
 * @see simplefm.SimpleFMPackage#getBinaryConstraint()
 * @model abstract="true"
 * @generated
 */
public interface BinaryConstraint extends Constraint {
	/**
	 * Returns the value of the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Left</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Left</em>' containment reference.
	 * @see #setLeft(Constraint)
	 * @see simplefm.SimpleFMPackage#getBinaryConstraint_Left()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Constraint getLeft();

	/**
	 * Sets the value of the '{@link simplefm.BinaryConstraint#getLeft <em>Left</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Left</em>' containment reference.
	 * @see #getLeft()
	 * @generated
	 */
	void setLeft(Constraint value);

	/**
	 * Returns the value of the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Right</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Right</em>' containment reference.
	 * @see #setRight(Constraint)
	 * @see simplefm.SimpleFMPackage#getBinaryConstraint_Right()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Constraint getRight();

	/**
	 * Sets the value of the '{@link simplefm.BinaryConstraint#getRight <em>Right</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Right</em>' containment reference.
	 * @see #getRight()
	 * @generated
	 */
	void setRight(Constraint value);

} // BinaryConstraint
