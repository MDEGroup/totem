/**
 */
package simplefm;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link simplefm.FeatureContainer#getChildren <em>Children</em>}</li>
 *   <li>{@link simplefm.FeatureContainer#getKind <em>Kind</em>}</li>
 * </ul>
 *
 * @see simplefm.SimpleFMPackage#getFeatureContainer()
 * @model abstract="true"
 * @generated
 */
public interface FeatureContainer extends EObject {
	/**
	 * Returns the value of the '<em><b>Children</b></em>' containment reference list.
	 * The list contents are of type {@link simplefm.Feature}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children</em>' containment reference list.
	 * @see simplefm.SimpleFMPackage#getFeatureContainer_Children()
	 * @model containment="true"
	 * @generated
	 */
	EList<Feature> getChildren();

	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * The literals are from the enumeration {@link simplefm.ChildrenKind}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kind</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see simplefm.ChildrenKind
	 * @see #setKind(ChildrenKind)
	 * @see simplefm.SimpleFMPackage#getFeatureContainer_Kind()
	 * @model required="true"
	 * @generated
	 */
	ChildrenKind getKind();

	/**
	 * Sets the value of the '{@link simplefm.FeatureContainer#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind</em>' attribute.
	 * @see simplefm.ChildrenKind
	 * @see #getKind()
	 * @generated
	 */
	void setKind(ChildrenKind value);

} // FeatureContainer
