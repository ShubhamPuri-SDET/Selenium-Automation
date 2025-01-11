Here’s a **well-organized set of notes** you can add to your Git repository for future reference:

---

# **XPath Cheat Sheet for Selenium**
## **Introduction**
XPath is a powerful selector used in Selenium to navigate through elements and attributes in an XML or HTML document.

---

## **Basic Syntax**
- Locate element by attribute:
  ```xpath
  //tagname[@attribute='value']
  ```
  Example: `//input[@id='username']`

- Locate all elements:
  ```xpath
  //tagname
  ```
  Example: `//div`

---

## **Text-Based XPath**
1. **Exact Match:**
   ```xpath
   //tagname[text()='exactText']
   ```
   Example: `//button[text()='Submit']`

2. **Contains Text:**
   ```xpath
   //tagname[contains(text(), 'partialText')]
   ```
   Example: `//p[contains(text(), 'Error')]`

---

## **Attribute-Based XPath**
1. **Exact Match:**
   ```xpath
   //tagname[@attribute='value']
   ```
   Example: `//input[@name='email']`

2. **Contains Attribute:**
   ```xpath
   //tagname[contains(@attribute, 'partialValue')]
   ```
   Example: `//div[contains(@class, 'alert')]`

3. **Starts With:**
   ```xpath
   //tagname[starts-with(@attribute, 'startValue')]
   ```
   Example: `//input[starts-with(@name, 'user')]`

4. **Ends With:** (XPath 2.0+)
   ```xpath
   //tagname[ends-with(@attribute, 'endValue')]
   ```
   Example: `//input[ends-with(@id, 'field')]`

---

## **Logical Operators**
1. **AND Condition:**
   ```xpath
   //tagname[@attribute1='value1' and @attribute2='value2']
   ```
   Example: `//input[@type='text' and @name='email']`

2. **OR Condition:**
   ```xpath
   //tagname[@attribute1='value1' or @attribute2='value2']
   ```
   Example: `//input[@type='text' or @type='password']`

---

## **XPath Axes**
1. **Ancestor:**
   ```xpath
   //tagname[@attribute='value']/ancestor::ancestorTag
   ```
   Example: `//span[text()='Login']/ancestor::div`

2. **Descendant:**
   ```xpath
   //tagname[@attribute='value']/descendant::descendantTag
   ```
   Example: `//div[@id='container']/descendant::a`

3. **Child:**
   ```xpath
   //tagname[@attribute='value']/child::childTag
   ```
   Example: `//ul[@id='menu']/child::li`

4. **Following:**
   ```xpath
   //tagname[@attribute='value']/following::followingTag
   ```
   Example: `//h2[text()='Title']/following::p`

5. **Preceding:**
   ```xpath
   //tagname[@attribute='value']/preceding::precedingTag
   ```
   Example: `//input[@id='password']/preceding::label`

6. **Following-Sibling:**
   ```xpath
   //tagname[@attribute='value']/following-sibling::siblingTag
   ```
   Example: `//label[text()='Username']/following-sibling::input`

7. **Preceding-Sibling:**
   ```xpath
   //tagname[@attribute='value']/preceding-sibling::siblingTag
   ```
   Example: `//input[@id='email']/preceding-sibling::label`

---

## **Position-Based XPath**
1. **First Element:**
   ```xpath
   (//tagname[@attribute='value'])[1]
   ```
   Example: `(//input[@type='text'])[1]`

2. **Last Element:**
   ```xpath
   (//tagname[@attribute='value'])[last()]
   ```
   Example: `(//div[@class='row'])[last()]`

3. **Specific Position:**
   ```xpath
   (//tagname[@attribute='value'])[position()=n]
   ```
   Example: `(//li[@class='item'])[3]`

---

## **Wildcard XPath**
1. **Any Tag:**
   ```xpath
   //*[@attribute='value']
   ```
   Example: `//*[@id='header']`

2. **Any Attribute:**
   ```xpath
   //tagname[@*='value']
   ```
   Example: `//input[@*='username']`

---

## **Additional Tips**
1. Use `normalize-space()` to remove leading and trailing whitespace:
   ```xpath
   //tagname[normalize-space(text())='exactText']
   ```
   Example: `//div[normalize-space(text())='Welcome']`

2. Combine multiple XPath axes for advanced selection:
   ```xpath
   //div[@class='container']//a[starts-with(@href, 'http')]
   ```
   - Finds all `<a>` tags inside a `<div>` with `class='container'` where `href` starts with `http`.

---

Feel free to modify and expand this cheat sheet in your Git repository! Let me know if you want help formatting or enhancing it further.