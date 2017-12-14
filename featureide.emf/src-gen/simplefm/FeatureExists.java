/**
 */
package simplefm;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature Exists</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link simplefm.FeatureExists#getFeature <em>Feature</em>}</li>
 * </ul>
 *
 * @see simplefm.SimpleFMPackage#getFeatureExists()
 * @model
 * @generated
 */
public interface FeatureExists extends Constraint {
	/**
	 * Returns the value of the '<em><b>Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Feature</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature</em>' reference.
	 * @see #setFeature(Feature)
	 * @see simplefm.SimpleFMPackage#getFeatureExists_Feature()
	 * @model required="true"
	 * @generated
	 */
	Feature getFeature();

	/**
	 * Sets the value of the '{@link simplefm.FeatureExists#getFeature <em>Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feature</em>' reference.
	 * @see #getFeature()
	 * @generated
	 */
	void setFeature(Feature value);

} // FeatureExists
