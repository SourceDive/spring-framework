/*
 * Copyright 2002-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.transaction.interceptor;

import java.lang.reflect.Method;

import org.springframework.lang.Nullable;

/**
 * <p>事务属性源</p>
 * <p>事务属性元数据解析。这个是顶层的策略接口
 * <ul>解析 @Transactional</ul>
 * <ul>读取xml配置</ul>
 * <ul>处理编程式属性</ul>
 * </p>
 * <p>可以把它看作一个属性解析工厂，{@link TransactionAttributeSource#getTransactionAttribute(Method, Class)}是工厂方法。</p>
 * Strategy interface used by {@link TransactionInterceptor} for metadata retrieval.
 * <p>Implementations know how to source transaction attributes, whether from configuration,
 * metadata attributes at source level (such as annotations), or anywhere else.
 * <p>实现类具备获取事务属性的能力，无论这些属性来自配置、源码级元数据属性，还是其他任何来源。</p>
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 15.04.2003
 * @see TransactionInterceptor#setTransactionAttributeSource
 * @see TransactionProxyFactoryBean#setTransactionAttributeSource
 * @see org.springframework.transaction.annotation.AnnotationTransactionAttributeSource
 */
public interface TransactionAttributeSource {

	/**
	 * <p>判定给定的类的类上或者方法上是否可以解析到事务属性。
	 *     <ul>true: 可以提取事务属性。</ul>
	 *     <ul>false: 忽略。</ul>
	 * </p>
	 * Determine whether the given class is a candidate for transaction attributes
	 * in the metadata format of this {@code TransactionAttributeSource}.
	 * <p>If this method returns {@code false}, the methods on the given class
	 * will not get traversed for {@link #getTransactionAttribute} introspection.
	 * Returning {@code false} is therefore an optimization for non-affected
	 * classes, whereas {@code true} simply means that the class needs to get
	 * fully introspected for each method on the given class individually.
	 * @param targetClass the class to introspect
	 * @return {@code false} if the class is known to have no transaction
	 * attributes at class or method level; {@code true} otherwise. The default
	 * implementation returns {@code true}, leading to regular introspection.
	 * @since 5.2
	 */
	default boolean isCandidateClass(Class<?> targetClass) {
		return true;
	}

	/**
	 * <p>返回给定方法上匹配的事务属性。</p>
	 * Return the transaction attribute for the given method,
	 * or {@code null} if the method is non-transactional.
	 * @param method the method to introspect
	 * @param targetClass the target class (may be {@code null},
	 * in which case the declaring class of the method must be used)
	 * @return the matching transaction attribute, or {@code null} if none found
	 */
	@Nullable
	TransactionAttribute getTransactionAttribute(Method method, @Nullable Class<?> targetClass);

}
