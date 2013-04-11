package OpenMDS.api;

/**
 * This is a dummy interface designed to separate attunable items from non-attunable ones.
 * In reality, the attunement is stored in metadata; it's a value from 0 to 4095, representing the combination of 3 Colours enums.
 * For more details, see the methods AttunementToColours() and AttunementFromColours() in MDSUtils.class.
 * This interface is intended to be used on Items, not Blocks.
 */
public interface IAttunable
{

}