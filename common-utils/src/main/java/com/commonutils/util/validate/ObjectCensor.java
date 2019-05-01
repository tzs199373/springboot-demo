package com.commonutils.util.validate;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * ����Ϊobject����࣬���object�Ƿ�Ϊ�գ���������ģʽΪ�����޶���ģʽ
 */
public class ObjectCensor
{
    private ObjectCensor()
    {}


    /**
     * ���object�Ƿ�Ϊnull
     *  true(0bjectΪ��) -false(object��Ϊ��)
     * @param obj - ��Ҫ����0bject
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
     * ����ַ����Ƿ�Ϊ""
     * -true(�ַ���Ϊ"") -false(�ַ�����Ϊ"")
     * @param str - ��Ҫ�����ַ���
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
     * ���Map��size�Ƿ�Ϊ0
     * @param map - Map
     * @return boolean -true(Map��sizeΪ0) -false(Map��size��Ϊ0)
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
     * ���Collection��size�Ƿ�Ϊ0
     * @param collection - ��Ҫ����Collection
     * @return boolean -true(Collection��sizeΪ0) -false(Collection��size��Ϊ0)
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
     * ��������length�Ƿ�Ϊ0�����������������Ƿ�Ϊnull
     * @param obj - ��Ҫ����0bject
     * @return boolean -true(0bject[]Ϊ��) -false(object[]��Ϊ��)
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
     * ����ַ����Ƿ�Ϊ��
     *  true(0bject��Ϊ��) -false(objectΪ��)
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
     * @param list : Ҫ����List����
     *  true(0bject��Ϊ��) -false(objectΪ��)
     * @return
     * ���List�����Ƿ�Ϊ���Ƿ񳤶�Ϊ��
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
