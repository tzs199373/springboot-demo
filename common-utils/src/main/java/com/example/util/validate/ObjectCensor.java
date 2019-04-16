package com.example.util.validate;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 此类为object检查类，检查object是否为空，此类所用模式为无上限多例模式
 */
public class ObjectCensor
{
    private ObjectCensor()
    {}


    /**
     * 检查object是否为null
     *  true(0bject为空) -false(object不为空)
     * @param obj - 需要检查的0bject
     * @return boolean
     */
    public static boolean checkObjectIsNull( Object obj )
    {
        if ( obj == null )
        {
            return true;
        }
        else
        {
            if ( obj instanceof String )
            {
                return checkStringIsNull( ( String )obj );
            }
            else if ( obj instanceof Map )
            {
                return checkMapIsNull( ( Map )obj );
            }
            else if ( obj instanceof Collection )
            {
                return checkCollectionIsNull( ( Collection )obj );
            }
            else if ( obj instanceof Object[] )
            {
                return checkArrayIsNull( ( Object[] )obj );
            }
            else
            {
                return false;
            }
        }
    }

    /**
     * 检查字符串是否为""
     * -true(字符串为"") -false(字符串不为"")
     * @param str - 需要检查的字符串
     * @return boolean 
     */
    private static boolean checkStringIsNull( String str )
    {
        if ( "".equals( str ) || "null".equals( str ) || str==null )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 检查Map的size是否为0
     * @param map - Map
     * @return boolean -true(Map的size为0) -false(Map的size不为0)
     */
    private static boolean checkMapIsNull( Map map )
    {
	if( map.size()>0){
    		return false;
    	}else{
            return true;
    	}
    }

    /**
     * 检查Collection的size是否为0
     * @param collection - 需要检查的Collection
     * @return boolean -true(Collection的size为0) -false(Collection的size不为0)
     */
    private static boolean checkCollectionIsNull( Collection collection )
    {
    	if(collection.size()>0 ){
    		return false;
    	}else{
    		return true;
    	}
     }

    /**
     * 检查数组的length是否为0，检查数组里的数据是否都为null
     * @param obj - 需要检查的0bject
     * @return boolean -true(0bject[]为空) -false(object[]不为空)
     */
    private static boolean checkArrayIsNull( Object[] obj )
    {
        if ( obj.length == 0 )
        {
            return true;
        }
        else
        {
            for ( int i = 0; i < obj.length; i++ )
            {
                if ( obj[i] != null )
                {
                    return false;
                }
            }

            return true;
        }
    }
    /**
     * 检查字符串是否为空
     *  true(0bject不为空) -false(object为空)
     * @param str
     * @return
     */
    public static boolean isStrRegular(String... str)
    {
    	for(int i=0,n=str.length;i<n;i++)
    	{
    		if(str[i]==null|| "".equals(str[i]))
    		{
    			return false;
    		}
    	}
    	return true;
    }
    
    /**
     * @author : tiankang
     * @param list : 要检查的List集合
     *  true(0bject不为空) -false(object为空)
     * @return
     * 检查List集合是否为空是否长度为零
     */
    public static boolean checkListIsNull(List list)
    {
    	if(list!=null && list.size()!=0)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
}
