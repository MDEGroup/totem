/**
 */
package simplefm;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see simplefm.SimpleFMFactory
 * @model kind="package"
 * @generated
 */
public interface SimpleFMPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "simplefm";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://simplefm.emf";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "simplefm.emf";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SimpleFMPackage eINSTANCE = simplefm.impl.SimpleFMPackageImpl.init();

	/**
	 * The meta object id for the '{@link simplefm.impl.FeatureContainerImpl <em>Feature Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see simplefm.impl.FeatureContainerImpl
	 * @see simplefm.impl.SimpleFMPackageImpl#getFeatureContainer()
	 * @generated
	 */
	int FEATURE_CONTAINER = 1;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CONTAINER__CHILDREN = 0;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CONTAINER__KIND = 1;

	/**
	 * The number of structural features of the '<em>Feature Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CONTAINER_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Feature Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CONTAINER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link simplefm.impl.FeatureModelImpl <em>Feature Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see simplefm.impl.FeatureModelImpl
	 * @see simplefm.impl.SimpleFMPackageImpl#getFeatureModel()
	 * @generated
	 */
	int FEATURE_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MODEL__CHILDREN = FEATURE_CONTAINER__CHILDREN;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MODEL__KIND = FEATURE_CONTAINER__KIND;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MODEL__NAME = FEATURE_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MODEL__CONSTRAINTS = FEATURE_CONTAINER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Feature Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MODEL_FEATURE_COUNT = FEATURE_CONTAINER_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Feature Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MODEL_OPERATION_COUNT = FEATURE_CONTAINER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link simplefm.impl.FeatureImpl <em>Feature</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see simplefm.impl.FeatureImpl
	 * @see simplefm.impl.SimpleFMPackageImpl#getFeature()
	 * @generated
	 */
	int FEATURE = 2;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__CHILDREN = FEATURE_CONTAINER__CHILDREN;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__KIND = FEATURE_CONTAINER__KIND;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__NAME = FEATURE_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Mandatory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__MANDATORY = FEATURE_CONTAINER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Feature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_FEATURE_COUNT = FEATURE_CONTAINER_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Feature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_OPERATION_COUNT = FEATURE_CONTAINER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link simplefm.impl.ConstraintImpl <em>Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see simplefm.impl.ConstraintImpl
	 * @see simplefm.impl.SimpleFMPackageImpl#getConstraint()
	 * @generated
	 */
	int CONSTRAINT = 3;

	/**
	 * The number of structural features of the '<em>Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link simplefm.impl.FeatureExistsImpl <em>Feature Exists</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see simplefm.impl.FeatureExistsImpl
	 * @see simplefm.impl.SimpleFMPackageImpl#getFeatureExists()
	 * @generated
	 */
	int FEATURE_EXISTS = 4;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_EXISTS__FEATURE = CONSTRAINT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Feature Exists</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_EXISTS_FEATURE_COUNT = CONSTRAINT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Feature Exists</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_EXISTS_OPERATION_COUNT = CONSTRAINT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link simplefm.impl.BinaryConstraintImpl <em>Binary Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see simplefm.impl.BinaryConstraintImpl
	 * @see simplefm.impl.SimpleFMPackageImpl#getBinaryConstraint()
	 * @generated
	 */
	int BINARY_CONSTRAINT = 5;

	/**
	 * The feature id for the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_CONSTRAINT__LEFT = CONSTRAINT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_CONSTRAINT__RIGHT = CONSTRAINT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Binary Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_CONSTRAINT_FEATURE_COUNT = CONSTRAINT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Binary Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_CONSTRAINT_OPERATION_COUNT = CONSTRAINT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link simplefm.impl.AndExprImpl <em>And Expr</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see simplefm.impl.AndExprImpl
	 * @see simplefm.impl.SimpleFMPackageImpl#getAndExpr()
	 * @generated
	 */
	int AND_EXPR = 6;

	/**
	 * The feature id for the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AND_EXPR__LEFT = BINARY_CONSTRAINT__LEFT;

	/**
	 * The feature id for the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AND_EXPR__RIGHT = BINARY_CONSTRAINT__RIGHT;

	/**
	 * The number of structural features of the '<em>And Expr</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AND_EXPR_FEATURE_COUNT = BINARY_CONSTRAINT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>And Expr</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AND_EXPR_OPERATION_COUNT = BINARY_CONSTRAINT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link simplefm.impl.OrExprImpl <em>Or Expr</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see simplefm.impl.OrExprImpl
	 * @see simplefm.impl.SimpleFMPackageImpl#getOrExpr()
	 * @generated
	 */
	int OR_EXPR = 7;

	/**
	 * The feature id for the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR_EXPR__LEFT = BINARY_CONSTRAINT__LEFT;

	/**
	 * The feature id for the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR_EXPR__RIGHT = BINARY_CONSTRAINT__RIGHT;

	/**
	 * The number of structural features of the '<em>Or Expr</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR_EXPR_FEATURE_COUNT = BINARY_CONSTRAINT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Or Expr</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR_EXPR_OPERATION_COUNT = BINARY_CONSTRAINT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link simplefm.impl.ImpliesExprImpl <em>Implies Expr</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see simplefm.impl.ImpliesExprImpl
	 * @see simplefm.impl.SimpleFMPackageImpl#getImpliesExpr()
	 * @generated
	 */
	int IMPLIES_EXPR = 8;

	/**
	 * The feature id for the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLIES_EXPR__LEFT = BINARY_CONSTRAINT__LEFT;

	/**
	 * The feature id for the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLIES_EXPR__RIGHT = BINARY_CONSTRAINT__RIGHT;

	/**
	 * The number of structural features of the '<em>Implies Expr</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLIES_EXPR_FEATURE_COUNT = BINARY_CONSTRAINT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Implies Expr</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLIES_EXPR_OPERATION_COUNT = BINARY_CONSTRAINT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link simplefm.ChildrenKind <em>Children Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see simplefm.ChildrenKind
	 * @see simplefm.impl.SimpleFMPackageImpl#getChildrenKind()
	 * @generated
	 */
	int CHILDREN_KIND = 9;


	/**
	 * Returns the meta object for class '{@link simplefm.FeatureModel <em>Feature Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature Model</em>'.
	 * @see simplefm.FeatureModel
	 * @generated
	 */
	EClass getFeatureModel();

	/**
	 * Returns the meta object for the attribute '{@link simplefm.FeatureModel#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see simplefm.FeatureModel#getName()
	 * @see #getFeatureModel()
	 * @generated
	 */
	EAttribute getFeatureModel_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link simplefm.FeatureModel#getConstraints <em>Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Constraints</em>'.
	 * @see simplefm.FeatureModel#getConstraints()
	 * @see #getFeatureModel()
	 * @generated
	 */
	EReference getFeatureModel_Constraints();

	/**
	 * Returns the meta object for class '{@link simplefm.FeatureContainer <em>Feature Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature Container</em>'.
	 * @see simplefm.FeatureContainer
	 * @generated
	 */
	EClass getFeatureContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link simplefm.FeatureContainer#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Children</em>'.
	 * @see simplefm.FeatureContainer#getChildren()
	 * @see #getFeatureContainer()
	 * @generated
	 */
	EReference getFeatureContainer_Children();

	/**
	 * Returns the meta object for the attribute '{@link simplefm.FeatureContainer#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see simplefm.FeatureContainer#getKind()
	 * @see #getFeatureContainer()
	 * @generated
	 */
	EAttribute getFeatureContainer_Kind();

	/**
	 * Returns the meta object for class '{@link simplefm.Feature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature</em>'.
	 * @see simplefm.Feature
	 * @generated
	 */
	EClass getFeature();

	/**
	 * Returns the meta object for the attribute '{@link simplefm.Feature#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see simplefm.Feature#getName()
	 * @see #getFeature()
	 * @generated
	 */
	EAttribute getFeature_Name();

	/**
	 * Returns the meta object for the attribute '{@link simplefm.Feature#isMandatory <em>Mandatory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mandatory</em>'.
	 * @see simplefm.Feature#isMandatory()
	 * @see #getFeature()
	 * @generated
	 */
	EAttribute getFeature_Mandatory();

	/**
	 * Returns the meta object for class '{@link simplefm.Constraint <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constraint</em>'.
	 * @see simplefm.Constraint
	 * @generated
	 */
	EClass getConstraint();

	/**
	 * Returns the meta object for class '{@link simplefm.FeatureExists <em>Feature Exists</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature Exists</em>'.
	 * @see simplefm.FeatureExists
	 * @generated
	 */
	EClass getFeatureExists();

	/**
	 * Returns the meta object for the reference '{@link simplefm.FeatureExists#getFeature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Feature</em>'.
	 * @see simplefm.FeatureExists#getFeature()
	 * @see #getFeatureExists()
	 * @generated
	 */
	EReference getFeatureExists_Feature();

	/**
	 * Returns the meta object for class '{@link simplefm.BinaryConstraint <em>Binary Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Binary Constraint</em>'.
	 * @see simplefm.BinaryConstraint
	 * @generated
	 */
	EClass getBinaryConstraint();

	/**
	 * Returns the meta object for the containment reference '{@link simplefm.BinaryConstraint#getLeft <em>Left</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Left</em>'.
	 * @see simplefm.BinaryConstraint#getLeft()
	 * @see #getBinaryConstraint()
	 * @generated
	 */
	EReference getBinaryConstraint_Left();

	/**
	 * Returns the meta object for the containment reference '{@link simplefm.BinaryConstraint#getRight <em>Right</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Right</em>'.
	 * @see simplefm.BinaryConstraint#getRight()
	 * @see #getBinaryConstraint()
	 * @generated
	 */
	EReference getBinaryConstraint_Right();

	/**
	 * Returns the meta object for class '{@link simplefm.AndExpr <em>And Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>And Expr</em>'.
	 * @see simplefm.AndExpr
	 * @generated
	 */
	EClass getAndExpr();

	/**
	 * Returns the meta object for class '{@link simplefm.OrExpr <em>Or Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Or Expr</em>'.
	 * @see simplefm.OrExpr
	 * @generated
	 */
	EClass getOrExpr();

	/**
	 * Returns the meta object for class '{@link simplefm.ImpliesExpr <em>Implies Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Implies Expr</em>'.
	 * @see simplefm.ImpliesExpr
	 * @generated
	 */
	EClass getImpliesExpr();

	/**
	 * Returns the meta object for enum '{@link simplefm.ChildrenKind <em>Children Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Children Kind</em>'.
	 * @see simplefm.ChildrenKind
	 * @generated
	 */
	EEnum getChildrenKind();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SimpleFMFactory getSimpleFMFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link simplefm.impl.FeatureModelImpl <em>Feature Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see simplefm.impl.FeatureModelImpl
		 * @see simplefm.impl.SimpleFMPackageImpl#getFeatureModel()
		 * @generated
		 */
		EClass FEATURE_MODEL = eINSTANCE.getFeatureModel();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_MODEL__NAME = eINSTANCE.getFeatureModel_Name();

		/**
		 * The meta object literal for the '<em><b>Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_MODEL__CONSTRAINTS = eINSTANCE.getFeatureModel_Constraints();

		/**
		 * The meta object literal for the '{@link simplefm.impl.FeatureContainerImpl <em>Feature Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see simplefm.impl.FeatureContainerImpl
		 * @see simplefm.impl.SimpleFMPackageImpl#getFeatureContainer()
		 * @generated
		 */
		EClass FEATURE_CONTAINER = eINSTANCE.getFeatureContainer();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_CONTAINER__CHILDREN = eINSTANCE.getFeatureContainer_Children();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_CONTAINER__KIND = eINSTANCE.getFeatureContainer_Kind();

		/**
		 * The meta object literal for the '{@link simplefm.impl.FeatureImpl <em>Feature</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see simplefm.impl.FeatureImpl
		 * @see simplefm.impl.SimpleFMPackageImpl#getFeature()
		 * @generated
		 */
		EClass FEATURE = eINSTANCE.getFeature();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE__NAME = eINSTANCE.getFeature_Name();

		/**
		 * The meta object literal for the '<em><b>Mandatory</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE__MANDATORY = eINSTANCE.getFeature_Mandatory();

		/**
		 * The meta object literal for the '{@link simplefm.impl.ConstraintImpl <em>Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see simplefm.impl.ConstraintImpl
		 * @see simplefm.impl.SimpleFMPackageImpl#getConstraint()
		 * @generated
		 */
		EClass CONSTRAINT = eINSTANCE.getConstraint();

		/**
		 * The meta object literal for the '{@link simplefm.impl.FeatureExistsImpl <em>Feature Exists</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see simplefm.impl.FeatureExistsImpl
		 * @see simplefm.impl.SimpleFMPackageImpl#getFeatureExists()
		 * @generated
		 */
		EClass FEATURE_EXISTS = eINSTANCE.getFeatureExists();

		/**
		 * The meta object literal for the '<em><b>Feature</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_EXISTS__FEATURE = eINSTANCE.getFeatureExists_Feature();

		/**
		 * The meta object literal for the '{@link simplefm.impl.BinaryConstraintImpl <em>Binary Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see simplefm.impl.BinaryConstraintImpl
		 * @see simplefm.impl.SimpleFMPackageImpl#getBinaryConstraint()
		 * @generated
		 */
		EClass BINARY_CONSTRAINT = eINSTANCE.getBinaryConstraint();

		/**
		 * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINARY_CONSTRAINT__LEFT = eINSTANCE.getBinaryConstraint_Left();

		/**
		 * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINARY_CONSTRAINT__RIGHT = eINSTANCE.getBinaryConstraint_Right();

		/**
		 * The meta object literal for the '{@link simplefm.impl.AndExprImpl <em>And Expr</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see simplefm.impl.AndExprImpl
		 * @see simplefm.impl.SimpleFMPackageImpl#getAndExpr()
		 * @generated
		 */
		EClass AND_EXPR = eINSTANCE.getAndExpr();

		/**
		 * The meta object literal for the '{@link simplefm.impl.OrExprImpl <em>Or Expr</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see simplefm.impl.OrExprImpl
		 * @see simplefm.impl.SimpleFMPackageImpl#getOrExpr()
		 * @generated
		 */
		EClass OR_EXPR = eINSTANCE.getOrExpr();

		/**
		 * The meta object literal for the '{@link simplefm.impl.ImpliesExprImpl <em>Implies Expr</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see simplefm.impl.ImpliesExprImpl
		 * @see simplefm.impl.SimpleFMPackageImpl#getImpliesExpr()
		 * @generated
		 */
		EClass IMPLIES_EXPR = eINSTANCE.getImpliesExpr();

		/**
		 * The meta object literal for the '{@link simplefm.ChildrenKind <em>Children Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see simplefm.ChildrenKind
		 * @see simplefm.impl.SimpleFMPackageImpl#getChildrenKind()
		 * @generated
		 */
		EEnum CHILDREN_KIND = eINSTANCE.getChildrenKind();

	}

} //SimpleFMPackage
